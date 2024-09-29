package com.gradingsystem.gradingsystem.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final String uploadDir = "uploads/"; // Directory to store files

    public String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file.");
        }

        // Create the directory if it doesn't exist
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path); // Correct usage without LinkOption
        }

        // Save the file
        String filePath = uploadDir + file.getOriginalFilename();
        File destinationFile = new File(filePath);
        file.transferTo(destinationFile);
        return filePath; // Return the file path for saving in the database
    }
}
