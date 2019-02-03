package com.sdev.officefilemanager.web;

import com.sdev.officefilemanager.data.DocumentRepository;
import com.sdev.officefilemanager.data.FileRepository;
import com.sdev.officefilemanager.domain.Document;
import com.sdev.officefilemanager.domain.FileModel;
import com.sdev.officefilemanager.service.DocumentStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/")
public class DocumentController {

    private DocumentRepository repository;
    private DocumentStorageService service;
    private FileRepository fileRepository;
    private FileModel filedata;

    @Autowired
    public DocumentController(DocumentRepository repository, DocumentStorageService service, FileRepository fileRepository ){
        this.repository = repository;
        this.service= service;
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

        service.store(file, document.getDocumentType());
        String savedfilename= String.valueOf(service.getLocation(document.getDocumentType()));
        String savedFileLocationName= StringUtils.cleanPath(file.getOriginalFilename());

        filedata = new FileModel(savedfilename,savedFileLocationName);
        savedDocumentData.setFileModel(filedata);
        filedata.setDocumentfileid(savedDocumentData);
        this.repository.save(savedDocumentData);

        modelAndView.addObject("fileUploadMessage", "Your file Uploaded Successfully");
        modelAndView.setViewName("uploadDocument");
        return modelAndView;
    }

    @GetMapping("showdocument")
    public String displayAllDocument(ModelMap modelMap){
        Iterable<Document> documents= this.repository.findAll();
        modelMap.addAttribute("documents", documents);
        return "showDocument";
    }

    @GetMapping("delete/{id}")
    public ModelAndView deleteDocumentById(@PathVariable Long id, ModelAndView modelAndView){
        String deleteIdName= this.repository.findDocumentByDocumentId(id).getDocumentName();
        this.repository.deleteById(id);
        modelAndView.addObject("idDeleteMessage", deleteIdName +" deleted successfully");
        Iterable<Document> documents= this.repository.findAll();
        modelAndView.addObject("documents", documents);
        modelAndView.setViewName("showDocument");
        return modelAndView;
    }



    @GetMapping("showdocument/{id}")
    public ModelAndView showFileDataById(@PathVariable Long id, ModelAndView modelAndView){

        Optional<Document> documentData= this.repository.findById(id);
        modelAndView.addObject("filetype", documentData.get().getDocumentType());
        modelAndView.addObject("documentData",documentData.get());
        modelAndView.setViewName("singleDocument");
        return modelAndView;
    }

    @GetMapping("download/{id}")
    public ModelAndView downloadFileById(@PathVariable Long id, ModelAndView modelAndView){
        this.fileRepository.findById(id);
        return modelAndView;
    }


}
