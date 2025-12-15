package com.github.phanikb.nvd.cli.uri.download;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.github.phanikb.nvd.common.NvdProperties;
import com.github.phanikb.nvd.enums.FeedType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class CpeUriDownloadCommandTest {

    private CpeUriDownloadCommand cpeUriDownloadCommand;
    private UriDownloadCommand uriDownloadCommand;
    private MockedStatic<NvdProperties> nvdPropertiesMockedStatic;

    @BeforeEach
    void setUp() {
        cpeUriDownloadCommand = new CpeUriDownloadCommand();
        uriDownloadCommand = mock(UriDownloadCommand.class);
        cpeUriDownloadCommand.parent = uriDownloadCommand;

        when(uriDownloadCommand.getArchiveType()).thenReturn(com.github.phanikb.nvd.enums.ArchiveType.ZIP);

        NvdProperties nvdProperties = mock(NvdProperties.class);
        NvdProperties.Nvd nvd = mock(NvdProperties.Nvd.class);
        NvdProperties.EndpointAndUrl endpointAndUrl = mock(NvdProperties.EndpointAndUrl.class);
        when(nvdProperties.getNvd()).thenReturn(nvd);
        when(nvd.getCpe()).thenReturn(endpointAndUrl);
        when(endpointAndUrl.getMainUrl())
                .thenReturn("https://nvd.nist.gov/feeds/xml/cpe/2.2/official-cpe-dictionary_v2.2.xml.{archive-type}");

        nvdPropertiesMockedStatic = mockStatic(NvdProperties.class);
        nvdPropertiesMockedStatic.when(NvdProperties::getInstance).thenReturn(nvdProperties);
    }

    @AfterEach
    void tearDown() {
        nvdPropertiesMockedStatic.close();
    }

    @Test
    void testGetUrls() {
        String[] urls = cpeUriDownloadCommand.getUrls();
        assertEquals(1, urls.length);
        assertEquals("https://nvd.nist.gov/feeds/xml/cpe/2.2/official-cpe-dictionary_v2.2.xml.zip", urls[0]);
    }

    @Test
    void testGetFeedType() {
        assertEquals(FeedType.CPE, cpeUriDownloadCommand.getFeedType());
    }
}
