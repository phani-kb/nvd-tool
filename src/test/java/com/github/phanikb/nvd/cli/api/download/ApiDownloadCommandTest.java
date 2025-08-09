package com.github.phanikb.nvd.cli.api.download;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.phanikb.nvd.cli.DownloadCommand;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApiDownloadCommandTest {
    private ApiDownloadCommand command;
    private DownloadCommand parent;

    @BeforeEach
    void setUp() {
        parent = Mockito.mock(DownloadCommand.class);
        command = new ApiDownloadCommand();
        command.parent = parent;
    }

    @Test
    void testValidateOptions() {
        // Should not throw, just log
        Mockito.doNothing().when(parent).validateOptions();
        assertDoesNotThrow(() -> command.validateOptions());
    }

    @Test
    void testIsDeleteTempDirDelegatesToParent() {
        Mockito.when(parent.isDeleteTempDir()).thenReturn(true);
        assertTrue(command.isDeleteTempDir());
        Mockito.when(parent.isDeleteTempDir()).thenReturn(false);
        assertFalse(command.isDeleteTempDir());
    }

    @Test
    void testGetOutFilenameDelegatesToParentOrUsesUuid() {
        Mockito.when(parent.getOutFilename()).thenReturn("custom.json");
        assertEquals("custom.json", command.getOutFilename());
        Mockito.when(parent.getOutFilename()).thenReturn(null);
        String filename = command.getOutFilename();
        assertTrue(filename.endsWith(".json"));
        assertTrue(filename.contains(command.getUuid()));
    }
}
