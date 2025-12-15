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

class CweUriDownloadCommandTest {

    private CweUriDownloadCommand cweUriDownloadCommand;
    private MockedStatic<NvdProperties> nvdPropertiesMockedStatic;

    @BeforeEach
    void setUp() {
        cweUriDownloadCommand = new CweUriDownloadCommand();
        cweUriDownloadCommand.parent = mock(UriDownloadCommand.class);

        NvdProperties nvdProperties = mock(NvdProperties.class);
        NvdProperties.Nvd nvd = mock(NvdProperties.Nvd.class);
        NvdProperties.Url url = mock(NvdProperties.Url.class);
        when(nvdProperties.getNvd()).thenReturn(nvd);
        when(nvd.getCwe()).thenReturn(url);
        when(url.getValue()).thenReturn("https://cwe.mitre.org/data/xml/cwec_v4.14.xml.zip");

        nvdPropertiesMockedStatic = mockStatic(NvdProperties.class);
        nvdPropertiesMockedStatic.when(NvdProperties::getInstance).thenReturn(nvdProperties);
    }

    @AfterEach
    void tearDown() {
        nvdPropertiesMockedStatic.close();
    }

    @Test
    void testGetUrls() {
        String[] urls = cweUriDownloadCommand.getUrls();
        assertEquals(1, urls.length);
        assertEquals("https://cwe.mitre.org/data/xml/cwec_v4.14.xml.zip", urls[0]);
    }

    @Test
    void testGetFeedType() {
        assertEquals(FeedType.CWE, cweUriDownloadCommand.getFeedType());
    }
}
