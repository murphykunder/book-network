package com.alimurph.book.file;

import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileStorageService {

    @Value("${application.file.upload.photo-output-path}")
    private String fileUploadPath;

    Logger log = LoggerFactory.getLogger(this.getClass());

    public String saveFile(@Nonnull MultipartFile file, @Nonnull Long userId) {
        final String fileUploadSubPath = "users" + File.separator + userId;
        return uploadFile(file, fileUploadSubPath);
    }

    private String uploadFile(@Nonnull MultipartFile sourceFile, @Nonnull String fileUploadSubPath) {
        final String fullUploadPath = fileUploadPath + File.separator + fileUploadSubPath;
        File targetFolder = new File(fullUploadPath);
        if(!targetFolder.exists()){
            boolean folderCreated = targetFolder.mkdirs();
            if(!folderCreated){
                log.warn("Failed to create the target folder " + fullUploadPath);
                return null;
            }
        }

        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
        String targetFilePath = fullUploadPath + File.separator + simpleDateFormat.format(new Date()) + fileExtension;
        Path targetPath = Paths.get(targetFilePath);

        try {
            Files.write(targetPath, sourceFile.getBytes());
            log.info("File saved to " + targetFilePath);
            return targetFilePath;
        }
        catch(IOException exception){
            log.error("File was not saved", exception);
        }

        return null;
    }

    private String getFileExtension(String fileName) {

        if(fileName == null || fileName.isBlank()){
            return "";
        }

        final int dotIndex = fileName.lastIndexOf(".");
        if(dotIndex == -1){
            return "";
        }

        return fileName.substring(dotIndex + 1).toLowerCase();
    }
}
