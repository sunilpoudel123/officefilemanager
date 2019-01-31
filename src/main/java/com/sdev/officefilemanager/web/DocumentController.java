package com.sdev.officefilemanager.web;

import com.sdev.officefilemanager.domain.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class DocumentController {

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
    public String submitForm(Document document){


        return "uploadDocument";
    }

    @GetMapping("showdocument")
    public String displayAllDocument(){

        return "showDocument";
    }
}
