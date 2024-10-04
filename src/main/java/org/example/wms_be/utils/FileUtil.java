package org.example.wms_be.utils;

import lombok.experimental.UtilityClass;
import org.example.wms_be.exception.FileStorageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public class FileUtil {

    public static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("MultipartFile cannot be null or empty");
        }

        String currentDir = System.getProperty("user.dir");
        String originalFilename = multipartFile.getOriginalFilename();

        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("Original filename cannot be null or empty");
        }

        Path path = Paths.get(currentDir, originalFilename);
        File file = path.toFile();

        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            Files.deleteIfExists(file.toPath());
            throw new FileStorageException("Could not save file: " + e.getMessage());
        }
        return file;
    }

}
