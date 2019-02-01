package com.sdev.officefilemanager.service;

import com.sdev.officefilemanager.domain.Document;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
    void init();
    void store(MultipartFile file, Document.Type type);
    Path load(String filename);
    void deleteAll();
}
