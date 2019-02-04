package com.sdev.officefilemanager.web;

import com.sdev.officefilemanager.data.DocumentRepository;
import com.sdev.officefilemanager.data.FileRepository;
import com.sdev.officefilemanager.domain.Document;
import com.sdev.officefilemanager.domain.FileModel;
import com.sdev.officefilemanager.service.DocumentStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/")
public class DocumentController {


    private DocumentRepository documentRepository;
    private DocumentStorageService storageService;
    private FileRepository fileRepository;
    private FileModel filedata;
    Document document;

    @Autowired
    public DocumentController(DocumentRepository repository, DocumentStorageService service, FileRepository fileRepository ){
        this.documentRepository = repository;
        this.storageService = service;
        this.fileRepository= fileRepository;
    }

    @ModelAttribute(value = "document")
    public Document newDocument()
    {
        return new Document();
    }

    @ModelAttribute("allTypes")
    public List<Document.Type> populateTypes() {
        return Arrays.asList(Document.Type.values());
    }


    @GetMapping(value="upload")
    public String displayUploadForm(Model model){

        return "uploadDocument";
    }

    @PostMapping(value ="/upload/add")
    public ModelAndView submitForm(@RequestParam("file") MultipartFile file, @ModelAttribute Document document, ModelAndView modelAndView){

        Document savedDocumentData= document.toDocumentData();

        storageService.store(file, document);
        String savedfilename= storageService.getFileName(StringUtils.cleanPath(file.getOriginalFilename()),savedDocumentData.getDocumentName());
        String savedFileLocationName= String.valueOf(storageService.getLocation(document));

        filedata = new FileModel(savedfilename,savedFileLocationName);
        savedDocumentData.setFileModel(filedata);
        filedata.setDocumentfileid(savedDocumentData);
        this.documentRepository.save(savedDocumentData);

        modelAndView.addObject("fileUploadMessage", "Your '"+file.getOriginalFilename() + "' file Uploaded Successfully");
        modelAndView.setViewName("uploadDocument");
        return modelAndView;
    }

    @GetMapping("showdocument")
    public String displayAllDocument(ModelMap modelMap){
        Iterable<Document> documents= this.documentRepository.findAll();
        modelMap.addAttribute("documents", documents);
        return "showDocument";
    }

    @GetMapping("delete/{id}")
    public ModelAndView deleteDocumentById(@PathVariable Long id, ModelAndView modelAndView){
        String deleteIdName= this.documentRepository.findDocumentByDocumentId(id).getDocumentName();
        Optional<FileModel> fileModel= this.fileRepository.findById(id);
        Optional<Document> loadNewDocumentData= this.documentRepository.findById(id);
        this.documentRepository.deleteById(id);
        storageService.deleteFileById(fileModel.get().getFileName(),loadNewDocumentData.get());
        modelAndView.addObject("idDeleteMessage", deleteIdName +" deleted successfully");
        Iterable<Document> documents= this.documentRepository.findAll();
        modelAndView.addObject("documents", documents);
        modelAndView.setViewName("showDocument");
        return modelAndView;
    }



    @GetMapping("showdocument/{id}")
    public ModelAndView showFileDataById(@PathVariable Long id, ModelAndView modelAndView){

        Optional<Document> documentData= this.documentRepository.findById(id);
        modelAndView.addObject("filetype", documentData.get().getDocumentType());
        modelAndView.addObject("documentData",documentData.get());
        modelAndView.setViewName("singleDocument");
        return modelAndView;
    }


    @GetMapping("download/{id}")
    public  ModelAndView prepareForDownloadFile(@PathVariable Long id, ModelAndView modelAndView) throws IOException {

        Optional<FileModel> fileModel= this.fileRepository.findById(id);
        Optional<Document> documentDataLoad = this.documentRepository.findById(id);
        document= documentDataLoad.get();
        Path path = Paths.get(fileModel.get().getFileName());
        modelAndView.addObject("entity", path);
        modelAndView.setViewName("downloadDocument");
        return modelAndView;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> beginDownloadFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename,document );
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
