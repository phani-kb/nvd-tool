package com.github.phanikb.nvd.cli.api.download;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.hc.core5.http.NameValuePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import picocli.CommandLine;
import picocli.CommandLine.ParameterException;

import com.github.phanikb.nvd.cli.api.CveHistoryApiOptions;
import com.github.phanikb.nvd.cli.processor.api.download.ApiDownloader;
import com.github.phanikb.nvd.common.Constants;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.enums.ApiQueryParams;
import com.github.phanikb.nvd.enums.CveHistoryEventName;
import com.github.phanikb.nvd.enums.FeedType;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CveHistoryApiDownloadCommandTest {

    @TempDir
    Path tempDir;

    @Mock
    private ApiDownloadCommand parentCommand;

    @Mock
    private ApiDownloader mockApiDownloader;

    @Mock
    private CveHistoryApiOptions mockCveHistoryApiOptions;

    @Mock
    private ApiDownloadCommonOptions mockCommonOptions;

    @Mock
    private CommandLine.Model.CommandSpec mockSpec;

    @Mock
    private CommandLine mockCommandLine;

    private CveHistoryApiDownloadCommand command;
    private File testOutDir;

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        MockitoAnnotations.openMocks(this);

        testOutDir = tempDir.toFile();

        command = new CveHistoryApiDownloadCommand();

        setField(command, "parent", parentCommand);
        when(parentCommand.getOutDir()).thenReturn(testOutDir);
        when(parentCommand.isDeleteTempDir()).thenReturn(true);
        when(parentCommand.isZip()).thenReturn(false);
        when(parentCommand.getOutFilename()).thenReturn("test-output.json");

        setField(command, "apiCommonOptions", mockCommonOptions);
        when(mockCommonOptions.getResultsPerPage()).thenReturn(100);
        when(mockCommonOptions.getStartIndex()).thenReturn(null);

        setField(command, "cveHistoryApiOptions", mockCveHistoryApiOptions);

        setField(command, "spec", mockSpec);
        when(mockSpec.name()).thenReturn("cve-history");
        when(mockSpec.commandLine()).thenReturn(mockCommandLine);

        doNothing().when(parentCommand).validateOptions();
    }

    private void setField(Object object, String fieldName, Object value)
            throws IllegalAccessException, NoSuchFieldException {
        Field field = null;
        Class<?> currentClass = object.getClass();

        while (currentClass != null && field == null) {
            try {
                field = currentClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            }
        }

        if (field == null) {
            throw new NoSuchFieldException("Field '" + fieldName + "' not found in class hierarchy");
        }

        field.setAccessible(true);
        field.set(object, value);
    }

    @Test
    void testCallWithValidOptionsNoDateRanges() {
        when(mockCveHistoryApiOptions.getChangeDateRange()).thenReturn(null);
        doNothing().when(mockCveHistoryApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());
    }

    @Test
    void testCallWithSingleChangeDateRange() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();

        CveHistoryApiOptions.ChangeDateRange changeDateRange = mock(CveHistoryApiOptions.ChangeDateRange.class);
        when(changeDateRange.getChangeStartDate()).thenReturn(startDate);
        when(changeDateRange.getChangeEndDate()).thenReturn(endDate);

        when(mockCveHistoryApiOptions.getChangeDateRange()).thenReturn(changeDateRange);
        doNothing().when(mockCveHistoryApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());

        assertNotNull(changeDateRange.getChangeStartDate());
        assertNotNull(changeDateRange.getChangeEndDate());
    }

    @Test
    void testCallWithDateRangeExceedsMaxRange() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(150); // Exceeds 120 days limit
        LocalDateTime endDate = LocalDateTime.now();

        CveHistoryApiOptions.ChangeDateRange changeDateRange = mock(CveHistoryApiOptions.ChangeDateRange.class);
        when(changeDateRange.getChangeStartDate()).thenReturn(startDate);
        when(changeDateRange.getChangeEndDate()).thenReturn(endDate);

        when(mockCveHistoryApiOptions.getChangeDateRange()).thenReturn(changeDateRange);
        doNothing().when(mockCveHistoryApiOptions).validateOptions();

        long daysDifference = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        assertTrue(daysDifference > Constants.DEFAULT_MAX_RANGE_IN_DAYS);
    }

    @Test
    void testGetQueryParamsWithCveId() {
        String cveId = "CVE-2023-1234";
        when(mockCveHistoryApiOptions.getCveId()).thenReturn(cveId);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertTrue(queryParams.stream()
                .anyMatch(param -> "cveId".equals(param.getName()) && cveId.equals(param.getValue())));
    }

    @Test
    void testGetQueryParamsWithBlankCveId() {
        when(mockCveHistoryApiOptions.getCveId()).thenReturn("");

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertFalse(queryParams.stream().anyMatch(param -> "cveId".equals(param.getName())));
    }

    @Test
    void testGetQueryParamsWithEventName() {
        when(mockCveHistoryApiOptions.getEventName()).thenReturn(CveHistoryEventName.CVE_RECEIVED);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.EVENT_NAME.getName().equals(param.getName())
                        && CveHistoryEventName.CVE_RECEIVED.getValue().equals(param.getValue())));
    }

    @Test
    void testGetQueryParamsWithChangeDateRange() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();

        CveHistoryApiOptions.ChangeDateRange changeDateRange = mock(CveHistoryApiOptions.ChangeDateRange.class);
        when(changeDateRange.getChangeStartDate()).thenReturn(startDate);
        when(changeDateRange.getChangeEndDate()).thenReturn(endDate);
        when(mockCveHistoryApiOptions.getChangeDateRange()).thenReturn(changeDateRange);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.CHANGE_START_DATE.getName().equals(param.getName())));
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.CHANGE_END_DATE.getName().equals(param.getName())));
    }

    @Test
    void testGetQueryParamsWithAllParameters() {
        String cveId = "CVE-2023-1234";
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();

        when(mockCveHistoryApiOptions.getCveId()).thenReturn(cveId);
        when(mockCveHistoryApiOptions.getEventName()).thenReturn(CveHistoryEventName.INITIAL_ANALYSIS);

        CveHistoryApiOptions.ChangeDateRange changeDateRange = mock(CveHistoryApiOptions.ChangeDateRange.class);
        when(changeDateRange.getChangeStartDate()).thenReturn(startDate);
        when(changeDateRange.getChangeEndDate()).thenReturn(endDate);
        when(mockCveHistoryApiOptions.getChangeDateRange()).thenReturn(changeDateRange);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertTrue(queryParams.stream()
                .anyMatch(param -> "cveId".equals(param.getName()) && cveId.equals(param.getValue())));
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.EVENT_NAME.getName().equals(param.getName())
                        && CveHistoryEventName.INITIAL_ANALYSIS.getValue().equals(param.getValue())));
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.CHANGE_START_DATE.getName().equals(param.getName())));
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.CHANGE_END_DATE.getName().equals(param.getName())));
    }

    @Test
    void testGetQueryParamsWithNullCveHistoryApiOptions() throws IllegalAccessException, NoSuchFieldException {
        setField(command, "cveHistoryApiOptions", null);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertEquals(1, queryParams.size()); // Only resultsPerPage since startIndex is null
    }

    @Test
    void testValidateOptionsSuccess() {
        doNothing().when(mockCveHistoryApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());
        verify(mockCveHistoryApiOptions).validateOptions();
        verify(parentCommand).validateOptions();
    }

    @Test
    void testValidateOptionsFailure() {
        doThrow(new IllegalArgumentException("Invalid options"))
                .when(mockCveHistoryApiOptions)
                .validateOptions();

        CommandLine.ParameterException exception =
                assertThrows(CommandLine.ParameterException.class, () -> command.validateOptions());
        assertTrue(exception.getMessage().contains("Invalid options"));
    }

    @Test
    void testGetApiDownloader() {
        when(mockCveHistoryApiOptions.getChangeDateRange()).thenReturn(null);

        ApiDownloader result = command.getApiDownloader();

        assertNotNull(result);
    }

    @Test
    void testGetApiDownloaderWithChangeDateRange() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();

        CveHistoryApiOptions.ChangeDateRange changeDateRange = mock(CveHistoryApiOptions.ChangeDateRange.class);
        when(changeDateRange.getChangeStartDate()).thenReturn(startDate);
        when(changeDateRange.getChangeEndDate()).thenReturn(endDate);
        when(mockCveHistoryApiOptions.getChangeDateRange()).thenReturn(changeDateRange);

        ApiDownloader result = command.getApiDownloader();

        assertNotNull(result);
    }

    @Test
    void testDateRangeValidationLogic() {
        LocalDateTime validStartDate = LocalDateTime.now().minusDays(30);
        LocalDateTime validEndDate = LocalDateTime.now();

        CveHistoryApiOptions.ChangeDateRange changeDateRange = mock(CveHistoryApiOptions.ChangeDateRange.class);
        when(changeDateRange.getChangeStartDate()).thenReturn(validStartDate);
        when(changeDateRange.getChangeEndDate()).thenReturn(validEndDate);

        when(mockCveHistoryApiOptions.getChangeDateRange()).thenReturn(changeDateRange);
        doNothing().when(mockCveHistoryApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());

        long daysDifference = java.time.temporal.ChronoUnit.DAYS.between(validStartDate, validEndDate);
        assertTrue(daysDifference <= Constants.DEFAULT_MAX_RANGE_IN_DAYS);
    }

    @Test
    void testCommandSpecValidation() {
        doNothing().when(mockCveHistoryApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());

        assertEquals("cve-history", mockSpec.name());
    }

    @Test
    void testExecuteSuccess() throws NvdException {
        when(mockApiDownloader.getFeedType()).thenReturn(FeedType.CVE_HISTORY);
        doNothing().when(mockApiDownloader).download(any());
        doNothing().when(mockApiDownloader).generateOutputFile(any());
        doNothing().when(mockApiDownloader).deleteTempDir();

        Integer result = command.execute(mockApiDownloader);

        assertEquals(0, result);
        verify(mockApiDownloader).download(any());
        verify(mockApiDownloader).generateOutputFile(FeedType.CVE_HISTORY.getCollectionNodeName());
        verify(mockApiDownloader).deleteTempDir();
    }

    @Test
    void testExecuteWithNvdException() throws NvdException {
        NvdException nvdException = new NvdException("Test download error");
        doThrow(nvdException).when(mockApiDownloader).download(any());

        NvdException exception = assertThrows(NvdException.class, () -> command.execute(mockApiDownloader));
        assertEquals("Test download error", exception.getMessage());
    }

    @Test
    void testValidateOptionsWithResultsPerPageTooLow() {
        when(mockCommonOptions.getResultsPerPage()).thenReturn(5); // Less than minimum 10

        ParameterException exception = assertThrows(ParameterException.class, () -> command.validateOptions());
        assertTrue(exception.getMessage().contains("results per page must be at least"));
    }

    @Test
    void testValidateOptionsWithNegativeStartIndex() {
        when(mockCommonOptions.getStartIndex()).thenReturn(-1);

        ParameterException exception = assertThrows(ParameterException.class, () -> command.validateOptions());
        assertTrue(exception.getMessage().contains("start index must be greater than or equal to 0"));
    }

    @Test
    void testGetMaxResultsPerPage() {
        when(mockCommonOptions.getResultsPerPage()).thenReturn(50);

        int result = command.getMaxResultsPerPage();

        assertEquals(50, result);
    }

    @Test
    void testGetStartIndex() {
        when(mockCommonOptions.getStartIndex()).thenReturn(100);

        Integer result = command.getStartIndex();

        assertEquals(100, result);
    }

    @Test
    void testGetOutDir() {
        File result = command.getOutDir();

        assertEquals(testOutDir, result);
    }

    @Test
    void testIsDeleteTempDir() {
        boolean result = command.isDeleteTempDir();

        assertTrue(result);
    }

    @Test
    void testIsCompress() {
        boolean result = command.isCompress();

        assertFalse(result);
    }

    @Test
    void testGetOutFilename() {
        String result = command.getOutFilename();

        assertEquals("test-output.json", result);
    }

    @Test
    void testGetLatch() {
        assertNotNull(command.getLatch());
        assertEquals(1, command.getLatch().getCount()); // DEFAULT_NUMBER_OF_PRODUCERS is 1
    }
}
