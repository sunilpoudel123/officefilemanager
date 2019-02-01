package com.sdev.officefilemanager.web;

import com.sdev.officefilemanager.data.DocumentRepository;
import com.sdev.officefilemanager.domain.Document;
import com.sdev.officefilemanager.service.DocumentStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class DocumentController {

    private DocumentRepository repository;
    private DocumentStorageService service;
    @Autowired
    public DocumentController(DocumentRepository repository, DocumentStorageService service){
        this.repository = repository;
        this.service= service;
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
    public String submitForm(@RequestParam("file") MultipartFile file, @ModelAttribute Document document, ModelMap modelMap){

        this.repository.save(document.toDocumentData());
        service.store(file, document.getDocumentType());
        return "uploadDocument";
    }

    @GetMapping("showdocument")
    public String displayAllDocument(ModelMap modelMap){
        Iterable<Document> documents= this.repository.findAll();
        modelMap.addAttribute("documents", documents);
        return "showDocument";
    }


}
