package com.gradingsystem.gradingsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir; // Directory to store files

    public String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file.");
        }

        try {
            // Print upload directory and file info
            System.out.println("Upload directory: " + new File(uploadDir).getAbsolutePath());
            System.out.println("File received: " + file.getOriginalFilename());
            System.out.println("File size: " + file.getSize());

            // Create the directory if it doesn't exist
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path); // Create directories
            }

            // Save the file
            String filePath = uploadDir + file.getOriginalFilename();
            Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            return filePath; // Return the file path for saving in the database

        } catch (IOException e) {
            e.printStackTrace(); // Log the complete stack trace
            throw new RuntimeException("Failed to store file: " + e.getMessage(), e);
        }
    }
}

