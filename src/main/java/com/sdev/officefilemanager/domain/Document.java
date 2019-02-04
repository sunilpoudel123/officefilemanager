package com.sdev.officefilemanager.domain;

import javax.persistence.*;

@Entity
@Table(name = "DOCUMENT")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DOCUMENTID")
    private Long documentId;

    @Column(name="DOCUMENTNAME")
    private String documentName;

    @Column(name = "DOCUMENTTYPE")
    private Type documentType;

    public enum Type {
        others,pdf,image,doc
    }

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "documentfileid")
    private FileModel fileModel;

    public FileModel getFileModel() {
        return fileModel;
    }

    public void setFileModel(FileModel fileModel) {
        this.fileModel = fileModel;
    }
    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Type getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Type documentType) {
        this.documentType = documentType;
    }

    public Document toDocumentData(){
        Document document= new Document();
        document.setDocumentId(this.documentId);
        document.setDocumentName(this.documentName);
        document.setDocumentType(this.documentType);
        return document;
    }
    public Document dummyDocumentData(){
        Document document= new Document();
        document.setDocumentId((long)1);
        document.setDocumentName("test");
        document.setDocumentType(Type.others);
        return document;
    }


}
