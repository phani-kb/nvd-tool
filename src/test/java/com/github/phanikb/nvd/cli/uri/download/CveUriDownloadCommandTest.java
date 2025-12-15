package com.github.phanikb.nvd.cli.uri.download;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import picocli.CommandLine;

import com.github.phanikb.nvd.common.NvdProperties;
import com.github.phanikb.nvd.enums.CveDownloadType;
import com.github.phanikb.nvd.enums.FeedType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CveUriDownloadCommandTest {

    private CveUriDownloadCommand cveUriDownloadCommand;
    private UriDownloadCommand uriDownloadCommand;
    private MockedStatic<NvdProperties> nvdPropertiesMockedStatic;

    @BeforeEach
    void setUp() {
        cveUriDownloadCommand = spy(new CveUriDownloadCommand());
        uriDownloadCommand = mock(UriDownloadCommand.class);
        cveUriDownloadCommand.parent = uriDownloadCommand;
        cveUriDownloadCommand.spec = new CommandLine(cveUriDownloadCommand).getCommandSpec();

        when(uriDownloadCommand.getArchiveType()).thenReturn(com.github.phanikb.nvd.enums.ArchiveType.ZIP);

        NvdProperties nvdProperties = mock(NvdProperties.class);
        NvdProperties.Nvd nvd = mock(NvdProperties.Nvd.class);
        NvdProperties.EndpointAndUrl endpointAndUrl = mock(NvdProperties.EndpointAndUrl.class);
        when(nvdProperties.getNvd()).thenReturn(nvd);
        when(nvd.getCve()).thenReturn(endpointAndUrl);
        when(endpointAndUrl.getMainUrl())
                .thenReturn("https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-{download-type}.json.{archive-type}");

        nvdPropertiesMockedStatic = mockStatic(NvdProperties.class);
        nvdPropertiesMockedStatic.when(NvdProperties::getInstance).thenReturn(nvdProperties);
    }

    @AfterEach
    void tearDown() {
        nvdPropertiesMockedStatic.close();
    }

    @Test
    void testValidateOptions_ValidYears() {
        setYears(new Integer[] {2022, 2023});
        cveUriDownloadCommand.validateOptions();
        verify(uriDownloadCommand).validateOptions();
    }

    @Test
    void testValidateOptions_InvalidYears() {
        setYears(new Integer[] {2001});
        assertThrows(CommandLine.ParameterException.class, () -> cveUriDownloadCommand.validateOptions());
    }

    @Test
    void testValidateOptions_DefaultYears() {
        cveUriDownloadCommand.validateOptions();
        verify(uriDownloadCommand).validateOptions();
    }

    @Test
    void testGetUrls_Full() {
        setYears(new Integer[] {2022, 2023});
        setDownloadType(CveDownloadType.FULL);
        String[] urls = cveUriDownloadCommand.getUrls();
        assertEquals(2, urls.length);
        assertEquals("https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-2022.json.zip", urls[0]);
        assertEquals("https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-2023.json.zip", urls[1]);
    }

    @Test
    void testGetUrls_Modified() {
        setDownloadType(CveDownloadType.MODIFIED);
        String[] urls = cveUriDownloadCommand.getUrls();
        assertEquals(1, urls.length);
        assertEquals("https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-modified.json.zip", urls[0]);
    }

    @Test
    void testGetUrls_Recent() {
        setDownloadType(CveDownloadType.RECENT);
        String[] urls = cveUriDownloadCommand.getUrls();
        assertEquals(1, urls.length);
        assertEquals("https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-recent.json.zip", urls[0]);
    }

    @Test
    void testGetFeedType() {
        assertEquals(FeedType.CVE, cveUriDownloadCommand.getFeedType());
    }

    private void setYears(Integer[] years) {
        try {
            java.lang.reflect.Field field = CveUriDownloadCommand.class.getDeclaredField("years");
            field.setAccessible(true);
            field.set(cveUriDownloadCommand, years);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDownloadType(CveDownloadType downloadType) {
        try {
            java.lang.reflect.Field field = CveUriDownloadCommand.class.getDeclaredField("downloadType");
            field.setAccessible(true);
            field.set(cveUriDownloadCommand, downloadType);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
