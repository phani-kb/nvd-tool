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

class CpeMatchUriDownloadCommandTest {

    private CpeMatchUriDownloadCommand cpeMatchUriDownloadCommand;
    private UriDownloadCommand uriDownloadCommand;
    private MockedStatic<NvdProperties> nvdPropertiesMockedStatic;

    @BeforeEach
    void setUp() {
        cpeMatchUriDownloadCommand = new CpeMatchUriDownloadCommand();
        uriDownloadCommand = mock(UriDownloadCommand.class);
        cpeMatchUriDownloadCommand.parent = uriDownloadCommand;

        when(uriDownloadCommand.getArchiveType()).thenReturn(com.github.phanikb.nvd.enums.ArchiveType.ZIP);

        NvdProperties nvdProperties = mock(NvdProperties.class);
        NvdProperties.Nvd nvd = mock(NvdProperties.Nvd.class);
        NvdProperties.EndpointAndUrl endpointAndUrl = mock(NvdProperties.EndpointAndUrl.class);
        when(nvdProperties.getNvd()).thenReturn(nvd);
        when(nvd.getCpeMatch()).thenReturn(endpointAndUrl);
        when(endpointAndUrl.getUrl())
                .thenReturn("https://nvd.nist.gov/feeds/json/cpematch/1.0/nvdcpematch-1.0.json.{archive-type}");

        nvdPropertiesMockedStatic = mockStatic(NvdProperties.class);
        nvdPropertiesMockedStatic.when(NvdProperties::getInstance).thenReturn(nvdProperties);
    }

    @AfterEach
    void tearDown() {
        nvdPropertiesMockedStatic.close();
    }

    @Test
    void testGetUrls() {
        String[] urls = cpeMatchUriDownloadCommand.getUrls();
        assertEquals(1, urls.length);
        assertEquals("https://nvd.nist.gov/feeds/json/cpematch/1.0/nvdcpematch-1.0.json.zip", urls[0]);
    }

    @Test
    void testGetFeedType() {
        assertEquals(FeedType.CPE_MATCH, cpeMatchUriDownloadCommand.getFeedType());
    }
}
