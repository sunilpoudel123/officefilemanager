package com.sdev.officefilemanager;

import com.sdev.officefilemanager.domain.Document;
import com.sdev.officefilemanager.service.DocumentStorageService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

/**
 * DocumentStorageService Tester.
 *
 * @author <Authors name>
 * @since <pre>Feb 1, 2019</pre>
 * @version 1.0
 */
public class DocumentStorageServiceTest {

    DocumentStorageService storageService;
    @Before
    public void before() throws Exception {
        storageService= new DocumentStorageService();
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void getCorrectFileName(){
        assertEquals("scr-2019-02.jpg",storageService.getFileName("screenshot.jpg"));
        assertEquals("rep-2019-02.pdf",storageService.getFileName("report.pdf"));
        assertEquals("res-2019-02.doc",storageService.getFileName("result.doc"));
        assertEquals("son-2019-02.mp3",storageService.getFileName("song.mp3"));
    }

    @Test
    public void getCorrectLocationToStoreFile(){
        assertEquals("document/doc",storageService.getLocation(Document.Type.doc).toString());
        assertEquals("document/pdf",storageService.getLocation(Document.Type.pdf).toString());
        assertEquals("document/others",storageService.getLocation(Document.Type.others).toString());
        assertEquals("document/image",storageService.getLocation(Document.Type.image).toString());
    }

} 
