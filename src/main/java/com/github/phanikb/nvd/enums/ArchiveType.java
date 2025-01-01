package com.github.phanikb.nvd.enums;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.io.IOUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArchiveType {
    ZIP(".zip"),
    GZ(".gz"),
    UNKNOWN("");

    private final String extension;

    public static ArchiveType of(String ext) {
        for (ArchiveType format : values()) {
            if (format.extension.equals(ext)) {
                return format;
            }
        }
        return ArchiveType.UNKNOWN;
    }

    public void extract(File src, File destDir) throws IOException, ArchiveException, CompressorException {
        if (this == UNKNOWN) {
            throw new IllegalArgumentException("unsupported file type: " + extension);
        }
        if (this == ZIP) {
            extractArchive(src, destDir);
        } else if (this == GZ) {
            extractCompressed(src, destDir);
        } else {
            throw new IllegalArgumentException("cannot extract non-archive file: " + src);
        }
    }

    private void extractArchive(File src, File destDir) throws IOException, ArchiveException {
        try (InputStream is = new FileInputStream(src);
             ArchiveInputStream<? extends ArchiveEntry> archiveInputStream = new ArchiveStreamFactory().createArchiveInputStream(
                     extension.substring(1), is)) {

            ArchiveEntry entry;
            while ((entry = archiveInputStream.getNextEntry()) != null) {
                File outputFile = new File(destDir, entry.getName());
                try (OutputStream os = new FileOutputStream(outputFile)) {
                    IOUtils.copy(archiveInputStream, os);
                }
            }
        }
    }

    private void extractCompressed(File src, File destDir) throws IOException, CompressorException {
        try (InputStream is = new FileInputStream(src);
             CompressorInputStream compressorInputStream = new CompressorStreamFactory().createCompressorInputStream(
                     extension.substring(1), is);
             OutputStream os = new FileOutputStream(new File(destDir, src.getName().replace(extension, "")))) {

            IOUtils.copy(compressorInputStream, os);
        }
    }

    public void archive(File src, File destDir) throws IOException, ArchiveException {
        if (this == ZIP) {
            try (ZipOutputStream zos = new ZipOutputStream(
                    new FileOutputStream(new File(destDir, src.getName() + extension)))) {
                zos.putNextEntry(new ZipEntry(src.getName()));
                try (FileInputStream fis = new FileInputStream(src)) {
                    IOUtils.copy(fis, zos);
                }
                zos.closeEntry();
            }
        } else if (this == GZ) {
            try (GZIPOutputStream gzos = new GZIPOutputStream(
                    new FileOutputStream(new File(destDir, src.getName() + extension)))) {
                try (FileInputStream fis = new FileInputStream(src)) {
                    IOUtils.copy(fis, gzos);
                }
            }
        } else {
            throw new IllegalArgumentException("unsupported archive type: " + extension);
        }
    }
}