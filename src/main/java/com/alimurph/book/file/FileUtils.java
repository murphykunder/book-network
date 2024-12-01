package com.alimurph.book.file;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    static Logger log = LoggerFactory.getLogger(FileUtils.class);
    public static byte[] readFileFromLocation(String fileUrl) {
        if(StringUtils.isBlank(fileUrl))
            return null;

        Path filePath = Path.of(fileUrl);
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.error("File not found at location " + fileUrl, e);
        }

        return null;
    }
}
