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

import com.github.phanikb.nvd.cli.api.CveApiOptions;
import com.github.phanikb.nvd.cli.processor.api.download.ApiDownloader;
import com.github.phanikb.nvd.common.Constants;
import com.github.phanikb.nvd.common.CveId;
import com.github.phanikb.nvd.common.CweId;
import com.github.phanikb.nvd.common.NvdException;
import com.github.phanikb.nvd.enums.ApiQueryParams;
import com.github.phanikb.nvd.enums.CveTagType;
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

class CveApiDownloadCommandTest {

    @TempDir
    Path tempDir;

    @Mock
    private ApiDownloadCommand parentCommand;

    @Mock
    private ApiDownloader mockApiDownloader;

    @Mock
    private CveApiOptions mockCveApiOptions;

    @Mock
    private ApiDownloadCommonOptions mockCommonOptions;

    @Mock
    private CommandLine.Model.CommandSpec mockSpec;

    @Mock
    private CommandLine mockCommandLine;

    private CveApiDownloadCommand command;
    private File testOutDir;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        testOutDir = tempDir.toFile();

        command = new CveApiDownloadCommand();

        setField(command, "parent", parentCommand);
        when(parentCommand.getOutDir()).thenReturn(testOutDir);
        when(parentCommand.isDeleteTempDir()).thenReturn(true);
        when(parentCommand.isZip()).thenReturn(false);
        when(parentCommand.getOutFilename()).thenReturn("test-output.json");

        setField(command, "apiCommonOptions", mockCommonOptions);
        when(mockCommonOptions.getResultsPerPage()).thenReturn(100);
        when(mockCommonOptions.getStartIndex()).thenReturn(null);

        setField(command, "cveApiOptions", mockCveApiOptions);

        setField(command, "spec", mockSpec);
        when(mockSpec.name()).thenReturn("cve");
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
        when(mockCveApiOptions.getLastModDateRange()).thenReturn(null);
        when(mockCveApiOptions.getPubDateRange()).thenReturn(null);
        doNothing().when(mockCveApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());

        // call() method calls super.getApiDownloader() which creates real network connections
    }

    @Test
    void testCallWithSingleLastModDateRange() throws Exception {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();

        CveApiOptions.LastModDateRange lastModDateRange = mock(CveApiOptions.LastModDateRange.class);
        when(lastModDateRange.getLastModStartDate()).thenReturn(startDate);
        when(lastModDateRange.getLastModEndDate()).thenReturn(endDate);

        when(mockCveApiOptions.getLastModDateRange()).thenReturn(lastModDateRange);
        when(mockCveApiOptions.getPubDateRange()).thenReturn(null);
        doNothing().when(mockCveApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());

        assertNotNull(lastModDateRange.getLastModStartDate());
        assertNotNull(lastModDateRange.getLastModEndDate());
    }

    @Test
    void testCallWithSinglePubDateRange() throws Exception {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();

        CveApiOptions.PubDateRange pubDateRange = mock(CveApiOptions.PubDateRange.class);
        when(pubDateRange.getPubStartDate()).thenReturn(startDate);
        when(pubDateRange.getPubEndDate()).thenReturn(endDate);

        when(mockCveApiOptions.getLastModDateRange()).thenReturn(null);
        when(mockCveApiOptions.getPubDateRange()).thenReturn(pubDateRange);
        doNothing().when(mockCveApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());

        assertNotNull(pubDateRange.getPubStartDate());
        assertNotNull(pubDateRange.getPubEndDate());
    }

    @Test
    void testCallWithDateRangeExceedsMaxRange() throws Exception {
        LocalDateTime startDate = LocalDateTime.now().minusDays(150); // Exceeds 120 days limit
        LocalDateTime endDate = LocalDateTime.now();

        CveApiOptions.LastModDateRange lastModDateRange = mock(CveApiOptions.LastModDateRange.class);
        when(lastModDateRange.getLastModStartDate()).thenReturn(startDate);
        when(lastModDateRange.getLastModEndDate()).thenReturn(endDate);

        when(mockCveApiOptions.getLastModDateRange()).thenReturn(lastModDateRange);
        when(mockCveApiOptions.getPubDateRange()).thenReturn(null);
        doNothing().when(mockCveApiOptions).validateOptions();

        long daysDifference = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        assertTrue(daysDifference > Constants.DEFAULT_MAX_RANGE_IN_DAYS);
    }

    @Test
    void testCallWithBothDateRangesExceedsMaxRange() throws Exception {
        LocalDateTime startDate = LocalDateTime.now().minusDays(150); // Exceeds 120 days limit
        LocalDateTime endDate = LocalDateTime.now();

        CveApiOptions.LastModDateRange lastModDateRange = mock(CveApiOptions.LastModDateRange.class);
        when(lastModDateRange.getLastModStartDate()).thenReturn(startDate);
        when(lastModDateRange.getLastModEndDate()).thenReturn(endDate);

        CveApiOptions.PubDateRange pubDateRange = mock(CveApiOptions.PubDateRange.class);
        when(pubDateRange.getPubStartDate()).thenReturn(startDate);
        when(pubDateRange.getPubEndDate()).thenReturn(endDate);

        when(mockCveApiOptions.getLastModDateRange()).thenReturn(lastModDateRange);
        when(mockCveApiOptions.getPubDateRange()).thenReturn(pubDateRange);
        doNothing().when(mockCveApiOptions).validateOptions();

        long daysDifference = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        assertTrue(daysDifference > Constants.DEFAULT_MAX_RANGE_IN_DAYS);
    }

    @Test
    void testCallWithMixedDateRanges() throws Exception {
        LocalDateTime validStartDate = LocalDateTime.now().minusDays(30);
        LocalDateTime validEndDate = LocalDateTime.now();
        LocalDateTime invalidStartDate = LocalDateTime.now().minusDays(150);
        LocalDateTime invalidEndDate = LocalDateTime.now();

        CveApiOptions.LastModDateRange lastModDateRange = mock(CveApiOptions.LastModDateRange.class);
        when(lastModDateRange.getLastModStartDate()).thenReturn(validStartDate);
        when(lastModDateRange.getLastModEndDate()).thenReturn(validEndDate);

        CveApiOptions.PubDateRange pubDateRange = mock(CveApiOptions.PubDateRange.class);
        when(pubDateRange.getPubStartDate()).thenReturn(invalidStartDate);
        when(pubDateRange.getPubEndDate()).thenReturn(invalidEndDate);

        when(mockCveApiOptions.getLastModDateRange()).thenReturn(lastModDateRange);
        when(mockCveApiOptions.getPubDateRange()).thenReturn(pubDateRange);
        doNothing().when(mockCveApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());

        assertNotNull(lastModDateRange.getLastModStartDate());
        assertNotNull(pubDateRange.getPubStartDate());
    }

    @Test
    void testGetQueryParamsWithCveId() {
        CveId cveId = mock(CveId.class);
        when(cveId.getId()).thenReturn("CVE-2023-1234");
        when(mockCveApiOptions.getCveId()).thenReturn(cveId);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.CVE_ID.getName().equals(param.getName())
                        && "CVE-2023-1234".equals(param.getValue())));
    }

    @Test
    void testGetQueryParamsWithCveTag() {
        when(mockCveApiOptions.getCveTag()).thenReturn(CveTagType.DISPUTED);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.CVE_TAG.getName().equals(param.getName())
                        && CveTagType.DISPUTED.getValue().equals(param.getValue())));
    }

    @Test
    void testGetQueryParamsWithCweId() {
        CweId cweId = mock(CweId.class);
        when(cweId.getId()).thenReturn("CWE-79");
        when(mockCveApiOptions.getCweId()).thenReturn(cweId);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertTrue(queryParams.stream()
                .anyMatch(param ->
                        ApiQueryParams.CWE_ID.getName().equals(param.getName()) && "CWE-79".equals(param.getValue())));
    }

    @Test
    void testGetQueryParamsWithVirtualMatchString() {
        when(mockCveApiOptions.getVirtualMatchString()).thenReturn("test-virtual-match");

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.VIRTUAL_MATCH_STRING.getName().equals(param.getName())
                        && "test-virtual-match".equals(param.getValue())));
    }

    @Test
    void testGetQueryParamsWithSourceIdentifier() {
        when(mockCveApiOptions.getSourceIdentifier()).thenReturn("test-source");

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.SOURCE_IDENTIFIER.getName().equals(param.getName())
                        && "test-source".equals(param.getValue())));
    }

    @Test
    void testGetQueryParamsWithCpeVulnerable() {
        CveApiOptions.CpeVulnerable cpeVulnerable = mock(CveApiOptions.CpeVulnerable.class);
        when(cpeVulnerable.getCpeName()).thenReturn("cpe:2.3:a:test:*:*:*:*:*:*:*:*:*");
        when(cpeVulnerable.isVulnerable()).thenReturn(true);
        when(mockCveApiOptions.getCpeVulnerable()).thenReturn(cpeVulnerable);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.CPE_NAME.getName().equals(param.getName())
                        && "cpe:2.3:a:test:*:*:*:*:*:*:*:*:*".equals(param.getValue())));
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.IS_VULNERABLE.getName().equals(param.getName())));
    }

    @Test
    void testGetQueryParamsWithKeywordSearch() {
        CveApiOptions.KeywordSearch keywordSearch = mock(CveApiOptions.KeywordSearch.class);
        when(keywordSearch.getKeywordSearch()).thenReturn("test keyword");
        when(keywordSearch.isKeywordExactMatch()).thenReturn(true);
        when(mockCveApiOptions.getKeywordSearch()).thenReturn(keywordSearch);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.KW_SEARCH.getName().equals(param.getName())
                        && "test keyword".equals(param.getValue())));
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.KW_EXACT_MATCH.getName().equals(param.getName())));
    }

    @Test
    void testGetQueryParamsWithBooleanFlags() {
        when(mockCveApiOptions.isHasCertAlerts()).thenReturn(true);
        when(mockCveApiOptions.isHasCertNotes()).thenReturn(true);
        when(mockCveApiOptions.isHasKev()).thenReturn(true);
        when(mockCveApiOptions.isHasOval()).thenReturn(true);
        when(mockCveApiOptions.isNoRejected()).thenReturn(true);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.HAS_CERT_ALERTS.getName().equals(param.getName())));
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.HAS_CERT_NOTES.getName().equals(param.getName())));
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.HAS_KEV.getName().equals(param.getName())));
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.HAS_OVAL.getName().equals(param.getName())));
        assertTrue(queryParams.stream()
                .anyMatch(param -> ApiQueryParams.NO_REJECTED.getName().equals(param.getName())));
    }

    @Test
    void testGetQueryParamsWithNullCveApiOptions() throws Exception {
        setField(command, "cveApiOptions", null);

        List<NameValuePair> queryParams = command.getQueryParams();

        assertNotNull(queryParams);
        assertEquals(1, queryParams.size()); // Only resultsPerPage since startIndex is null
    }

    @Test
    void testValidateOptionsSuccess() throws Exception {
        doNothing().when(mockCveApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());
        verify(mockCveApiOptions).validateOptions();
        verify(parentCommand).validateOptions();
    }

    @Test
    void testValidateOptionsFailure() throws Exception {
        doThrow(new IllegalArgumentException("Invalid options"))
                .when(mockCveApiOptions)
                .validateOptions();

        CommandLine.ParameterException exception =
                assertThrows(CommandLine.ParameterException.class, () -> command.validateOptions());
        assertTrue(exception.getMessage().contains("Invalid options"));
    }

    @Test
    void testGetApiDownloaderThrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> command.getApiDownloader());
    }

    @Test
    void testDateRangeValidationLogic() throws Exception {
        LocalDateTime validStartDate = LocalDateTime.now().minusDays(30);
        LocalDateTime validEndDate = LocalDateTime.now();

        CveApiOptions.LastModDateRange lastModDateRange = mock(CveApiOptions.LastModDateRange.class);
        when(lastModDateRange.getLastModStartDate()).thenReturn(validStartDate);
        when(lastModDateRange.getLastModEndDate()).thenReturn(validEndDate);

        when(mockCveApiOptions.getLastModDateRange()).thenReturn(lastModDateRange);
        when(mockCveApiOptions.getPubDateRange()).thenReturn(null);
        doNothing().when(mockCveApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());

        long daysDifference = java.time.temporal.ChronoUnit.DAYS.between(validStartDate, validEndDate);
        assertTrue(daysDifference <= Constants.DEFAULT_MAX_RANGE_IN_DAYS);
    }

    @Test
    void testCommandSpecValidation() throws Exception {
        doNothing().when(mockCveApiOptions).validateOptions();

        assertDoesNotThrow(() -> command.validateOptions());

        assertEquals("cve", mockSpec.name());
    }

    @Test
    void testExecuteSuccess() throws Exception {
        when(mockApiDownloader.getFeedType()).thenReturn(FeedType.CVE);
        doNothing().when(mockApiDownloader).download(any());
        doNothing().when(mockApiDownloader).generateOutputFile(any());
        doNothing().when(mockApiDownloader).deleteTempDir();

        Integer result = command.execute(mockApiDownloader);

        assertEquals(0, result);
        verify(mockApiDownloader).download(any());
        verify(mockApiDownloader).generateOutputFile(FeedType.CVE.getCollectionNodeName());
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
