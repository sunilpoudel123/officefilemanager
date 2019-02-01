package com.sdev.officefilemanager.service;

import com.sdev.officefilemanager.domain.Document;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import java.util.Date;

@Service
public class DocumentStorageService implements StorageService{

    private final Path documentLocation= Paths.get("document");


    public String getFileName(String filename){
        String timeStamp = new SimpleDateFormat("-yyyy-MM").format(new Date());
        String extension = FilenameUtils.getExtension(filename);
        String improvedFileName= filename.substring(0,3).concat(timeStamp).concat("."+ extension);
        return improvedFileName;
    }

    public Path getLocation(Document.Type type){
        Path improvedPath= documentLocation;
        switch (type){
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
    public void init() {
        try{
            Files.createDirectories(documentLocation);
        }
        catch (IOException e){
            throw new InternalError("could not create folder",e);
        }
    }

    @Override
    public void store(MultipartFile file, Document.Type type) {
        String filename= getFileName(StringUtils.cleanPath(file.getOriginalFilename()));
        try{
            if(file.isEmpty()){
                throw new InternalError("Failed to store empty file"+ filename);
            }
            try(InputStream stream= file.getInputStream()){
                Files.copy(stream,this.getLocation(type).resolve(filename));
            }
        }
        catch (IOException e){
            throw new InternalError("failed to store file ", e);
        }
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
