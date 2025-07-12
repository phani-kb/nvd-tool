package com.github.phanikb.nvd.cli.uri.download;

import org.junit.jupiter.api.Test;

import picocli.CommandLine;

import com.github.phanikb.nvd.cli.DownloadCommand;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class UriDownloadCommandTest {

    @Test
    void testCall() {
        UriDownloadCommand uriDownloadCommand = new UriDownloadCommand();
        uriDownloadCommand.spec = new CommandLine(new UriDownloadCommand()).getCommandSpec();
        assertThrows(CommandLine.ParameterException.class, uriDownloadCommand::call);
    }

    @Test
    void testValidateOptions() {
        UriDownloadCommand uriDownloadCommand = spy(new UriDownloadCommand());
        uriDownloadCommand.parent = mock(DownloadCommand.class);
        uriDownloadCommand.validateOptions();
        verify(uriDownloadCommand.parent).validateOptions();
    }
}
