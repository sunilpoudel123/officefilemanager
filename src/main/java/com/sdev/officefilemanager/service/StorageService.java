package com.sdev.officefilemanager.service;

import com.sdev.officefilemanager.domain.Document;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;

public interface StorageService {
    void store(MultipartFile file, Document document);
    Path load(String filename, Document document);
    void deleteFileById(String fullpath, Document document);
    Resource loadAsResource(String filename, Document document);
}
