package com.sdev.officefilemanager.domain;


import javax.persistence.*;

@Entity
@Table(name = "StoredFile")
public class FileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="FILEID")
    private Long FileId;

    @Column(name = "FILENAME")
    private String FileName;

    @Column(name = "FILELOCATION")
    private String FileLocation;

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "DocumentFileId", nullable = false)
    private Document documentfileid;

    public Document getDocumentfileid() {
        return documentfileid;
    }

    public void setDocumentfileid(Document documentfileid) {
        this.documentfileid = documentfileid;
    }

    public Long getFileId() {
        return FileId;
    }

    public void setFileId(Long fileId) {
        FileId = fileId;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFileLocation() {
        return FileLocation;
    }

    public void setFileLocation(String fileLocation) {
        FileLocation = fileLocation;
    }

    public FileModel() {

    }

    public FileModel(String fileName, String fileLocation) {
        FileName = fileName;
        FileLocation = fileLocation;
    }

    //    public FileModel toFileData(String FileName, String FileLocation){
//
//        FileModel file= new FileModel();
//        file.FileName=FileName;
//        file.FileLocation=FileLocation;
//        return file;
//
//    }
}
