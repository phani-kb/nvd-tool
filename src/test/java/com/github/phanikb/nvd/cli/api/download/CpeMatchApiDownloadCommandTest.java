package com.github.phanikb.nvd.cli.api.download;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.apache.hc.core5.http.NameValuePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import picocli.CommandLine;
import picocli.CommandLine.ParameterException;

import com.github.phanikb.nvd.cli.api.CpeMatchApiOptions;
import com.github.phanikb.nvd.cli.processor.api.download.ApiDownloader;
import com.github.phanikb.nvd.common.Constants;
import com.github.phanikb.nvd.common.CpeName;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.enums.ApiQueryParams;
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

class CpeMatchApiDownloadCommandTest {

    @TempDir
    Path tempDir;

    @Mock
    private ApiDownloadCommand parentCommand;

    @Mock
    private ApiDownloader mockApiDownloader;

    @Mock
    private CpeMatchApiOptions mockCpeMatchApiOptions;

    @Mock
    private ApiDownloadCommonOptions mockCommonOptions;

    @Mock
    private CommandLine.Model.CommandSpec mockSpec;

    @Mock
    private CommandLine mockCommandLine;

    private CpeMatchApiDownloadCommand command;
    private File testOutDir;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        testOutDir = tempDir.toFile();

        command = new CpeMatchApiDownloadCommand();

        setField(command, "parent", parentCommand);
        when(parentCommand.getOutDir()).thenReturn(testOutDir);
        when(parentCommand.isDeleteTempDir()).thenReturn(true);
        when(parentCommand.isZip()).thenReturn(false);
        when(parentCommand.getOutFilename()).thenReturn("test-output.json");

        setField(command, "apiCommonOptions", mockCommonOptions);
        when(mockCommonOptions.getResultsPerPage()).thenReturn(100);
        when(mockCommonOptions.getStartIndex()).thenReturn(null);

        setField(command, "cpeMatchApiOptions", mockCpeMatchApiOptions);

        setField(command, "spec", mockSpec);
        when(mockSpec.name()).thenReturn("cpe-match");
        when(mockSpec.commandLine()).thenReturn(mockCommandLine);

        doNothing().when(parentCommand).validateOptions();
    }

    private void setField(Object object, String fieldName, Object value) throws Exception {
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
    void testCallWithValidOptionsNoDateRanges() throws Exception {
        when(mockCpeMatchApiOptions.getLastModDateRange()).thenReturn(null);
        doNothing().when(mockCpeMatchApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());
    }

    @Test
    void testCallWithSingleLastModDateRange() throws Exception {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();

        CpeMatchApiOptions.LastModDateRange lastModDateRange = mock(CpeMatchApiOptions.LastModDateRange.class);
        when(lastModDateRange.getLastModStartDate()).thenReturn(startDate);
        when(lastModDateRange.getLastModEndDate()).thenReturn(endDate);

        when(mockCpeMatchApiOptions.getLastModDateRange()).thenReturn(lastModDateRange);
        doNothing().when(mockCpeMatchApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());

        assertNotNull(lastModDateRange.getLastModStartDate());
        assertNotNull(lastModDateRange.getLastModEndDate());
    }

    @Test
    void testCallWithDateRangeExceedsMaxRange() throws Exception {
        LocalDateTime startDate = LocalDateTime.now().minusDays(150); // Exceeds 120 days limit
        LocalDateTime endDate = LocalDateTime.now();

        CpeMatchApiOptions.LastModDateRange lastModDateRange = mock(CpeMatchApiOptions.LastModDateRange.class);
        when(lastModDateRange.getLastModStartDate()).thenReturn(startDate);
        when(lastModDateRange.getLastModEndDate()).thenReturn(endDate);

        when(mockCpeMatchApiOptions.getLastModDateRange()).thenReturn(lastModDateRange);
        doNothing().when(mockCpeMatchApiOptions).validateOptions();

        long daysDifference = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        assertTrue(daysDifference > Constants.DEFAULT_MAX_RANGE_IN_DAYS);
    }

    @Test
    void testGetQueryParamsWithMatchStringSearch() {
        CpeName cpeName = mock(CpeName.class);
        when(cpeName.getName()).thenReturn("cpe:2.3:a:test:*:*:*:*:*:*:*:*:*");
        when(mockCpeMatchApiOptions.getMatchStringSearch()).thenReturn(cpeName);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.MATCH_STRING_SEARCH.getName().equals(param.getName())
                        && "cpe:2.3:a:test:*:*:*:*:*:*:*:*:*".equals(param.getValue())));
    }

    @Test
    void testGetQueryParamsWithCveId() {
        String cveId = "CVE-2023-1234";
        when(mockCpeMatchApiOptions.getCveId()).thenReturn(cveId);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertTrue(queryParams.stream()
                .anyMatch(param ->
                        ApiQueryParams.CVE_ID.getName().equals(param.getName()) && cveId.equals(param.getValue())));
    }

    @Test
    void testGetQueryParamsWithMatchCriteriaId() {
        UUID matchCriteriaId = UUID.randomUUID();
        when(mockCpeMatchApiOptions.getMatchCriteriaId()).thenReturn(matchCriteriaId);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.MATCH_CRITERIA_ID.getName().equals(param.getName())
                        && matchCriteriaId.toString().equals(param.getValue())));
    }

    @Test
    void testGetQueryParamsWithLastModDateRange() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();

        CpeMatchApiOptions.LastModDateRange lastModDateRange = mock(CpeMatchApiOptions.LastModDateRange.class);
        when(lastModDateRange.getLastModStartDate()).thenReturn(startDate);
        when(lastModDateRange.getLastModEndDate()).thenReturn(endDate);
        when(mockCpeMatchApiOptions.getLastModDateRange()).thenReturn(lastModDateRange);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertTrue(queryParams.stream()
                .anyMatch(param ->
                        ApiQueryParams.LAST_MODIFIED_START_DATE.getName().equals(param.getName())));
        assertTrue(queryParams.stream()
                .anyMatch(
                        param -> ApiQueryParams.LAST_MODIFIED_END_DATE.getName().equals(param.getName())));
    }

    @Test
    void testGetQueryParamsWithBlankCveId() {
        when(mockCpeMatchApiOptions.getCveId()).thenReturn("");

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertFalse(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.CVE_ID.getName().equals(param.getName())));
    }

    @Test
    void testGetQueryParamsWithNullCpeMatchApiOptions() throws Exception {
        setField(command, "cpeMatchApiOptions", null);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertEquals(1, queryParams.size()); // Only resultsPerPage since startIndex is null
    }

    @Test
    void testValidateOptionsSuccess() throws Exception {
        doNothing().when(mockCpeMatchApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());
        verify(mockCpeMatchApiOptions).validateOptions();
        verify(parentCommand).validateOptions();
    }

    @Test
    void testValidateOptionsFailure() throws Exception {
        doThrow(new IllegalArgumentException("Invalid options"))
                .when(mockCpeMatchApiOptions)
                .validateOptions();

        CommandLine.ParameterException exception =
                assertThrows(CommandLine.ParameterException.class, () -> command.validateOptions());
        assertTrue(exception.getMessage().contains("Invalid options"));
    }

    @Test
    void testGetApiDownloader() {
        when(mockCpeMatchApiOptions.getLastModDateRange()).thenReturn(null);

        ApiDownloader result = command.getApiDownloader();

        assertNotNull(result);
    }

    @Test
    void testDateRangeValidationLogic() throws Exception {
        LocalDateTime validStartDate = LocalDateTime.now().minusDays(30);
        LocalDateTime validEndDate = LocalDateTime.now();

        CpeMatchApiOptions.LastModDateRange lastModDateRange = mock(CpeMatchApiOptions.LastModDateRange.class);
        when(lastModDateRange.getLastModStartDate()).thenReturn(validStartDate);
        when(lastModDateRange.getLastModEndDate()).thenReturn(validEndDate);

        when(mockCpeMatchApiOptions.getLastModDateRange()).thenReturn(lastModDateRange);
        doNothing().when(mockCpeMatchApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());

        long daysDifference = java.time.temporal.ChronoUnit.DAYS.between(validStartDate, validEndDate);
        assertTrue(daysDifference <= Constants.DEFAULT_MAX_RANGE_IN_DAYS);
    }

    @Test
    void testCommandSpecValidation() throws Exception {
        doNothing().when(mockCpeMatchApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());

        assertEquals("cpe-match", mockSpec.name());
    }

    @Test
    void testExecuteSuccess() throws Exception {
        when(mockApiDownloader.getFeedType()).thenReturn(FeedType.CPE_MATCH);
        doNothing().when(mockApiDownloader).download(any());
        doNothing().when(mockApiDownloader).generateOutputFile(any());
        doNothing().when(mockApiDownloader).deleteTempDir();

        Integer result = command.execute(mockApiDownloader);

        assertEquals(0, result);
        verify(mockApiDownloader).download(any());
        verify(mockApiDownloader).generateOutputFile(FeedType.CPE_MATCH.getCollectionNodeName());
        verify(mockApiDownloader).deleteTempDir();
    }

    @Test
    void testExecuteWithNvdException() throws Exception {
        NvdException nvdException = new NvdException("Test download error");
        doThrow(nvdException).when(mockApiDownloader).download(any());

        NvdException exception = assertThrows(NvdException.class, () -> command.execute(mockApiDownloader));
        assertEquals("Test download error", exception.getMessage());
    }

    @Test
    void testValidateOptionsWithResultsPerPageTooLow() throws Exception {
        when(mockCommonOptions.getResultsPerPage()).thenReturn(5); // Less than minimum 10

        ParameterException exception = assertThrows(ParameterException.class, () -> command.validateOptions());
        assertTrue(exception.getMessage().contains("results per page must be at least"));
    }

    @Test
    void testValidateOptionsWithNegativeStartIndex() throws Exception {
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
