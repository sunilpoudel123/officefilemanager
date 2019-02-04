package com.sdev.officefilemanager;

import com.sdev.officefilemanager.domain.Document;
import com.sdev.officefilemanager.service.DocumentStorageService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    Document document= new Document();
    @Before
    public void before() throws Exception {
        storageService= new DocumentStorageService();
        document= document.dummyDocumentData();
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void getCorrectFileName(){
        String timeStamp = new SimpleDateFormat("-yyyy-MM-dd-hh-mm").format(new Date());
        assertEquals("screen-scr"+timeStamp+".jpg",storageService.getFileName("screenshot.jpg","screen"));
        assertEquals("officereport-rep"+timeStamp+".pdf",storageService.getFileName("report.pdf","officereport"));
        assertEquals("document-res"+timeStamp+".doc",storageService.getFileName("result.doc", "document"));
        assertEquals("moviesong-son"+timeStamp+".mp3",storageService.getFileName("song.mp3","moviesong"));
    }

    @Test
    public void getCorrectLocationToStoreFile(){
        assertEquals("document/others",storageService.getLocation(document).toString());
    }

}
