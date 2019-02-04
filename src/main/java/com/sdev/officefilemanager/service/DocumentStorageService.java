package com.sdev.officefilemanager.service;

import com.sdev.officefilemanager.domain.Document;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import java.util.Date;

@Service
public class DocumentStorageService implements StorageService{

    private final Path documentLocation= Paths.get("document");

    public String getFileName(String filename, String suppliedname){
        String timeStamp = new SimpleDateFormat("-yyyy-MM-dd-hh-mm").format(new Date());
        String extension = FilenameUtils.getExtension(filename);
        String improvedFileName= suppliedname.concat("-").concat(filename.substring(0,3)).concat(timeStamp).concat("."+ extension);
        return improvedFileName;
    }

    public Path getLocation(Document document){
        Path improvedPath= documentLocation;
        switch (document.getDocumentType()){
            case doc:
                improvedPath = Paths.get(documentLocation.toString()+"/doc");
                break;
            case pdf:
                improvedPath = Paths.get(documentLocation.toString()+"/pdf");
                break;
            case image:
                improvedPath = Paths.get(documentLocation.toString()+"/image");
                break;
            case others:
                improvedPath = Paths.get(documentLocation.toString()+"/others");
                break;
        }
        return improvedPath;
    }

    @Override
    public void store(MultipartFile file, Document document) {
        String filename= getFileName(StringUtils.cleanPath(file.getOriginalFilename()), document.getDocumentName());
        try{
            if(file.isEmpty()){
                throw new InternalError("Failed to store empty file"+ filename);
            }
            try(InputStream stream= file.getInputStream()){
                Files.copy(stream,this.getLocation(document).resolve(filename));
            }
        }
        catch (IOException e){
            throw new InternalError("failed to store file ", e);
        }
    }

    @Override
    public Path load(String filename, Document document) {
        return getLocation(document).resolve(filename);
    }


    @Override
    public void deleteFileById(String fullpath, Document document) {
        Path file= load(fullpath, document);
        FileSystemUtils.deleteRecursively(file.toFile());
    }

    @Override
    public Resource loadAsResource(String filename, Document document) {
        try {
            Path file= load(filename, document);
            Resource resource= new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()){
                return resource;
            }
            else {
                throw new InternalError("no resource is found: ");
            }
        } catch (MalformedURLException e) {
            throw new InternalError(e);
        }
    }
}
