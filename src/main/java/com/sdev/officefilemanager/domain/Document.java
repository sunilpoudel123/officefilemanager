package com.sdev.officefilemanager.domain;

import javax.persistence.*;

@Entity
@Table(name = "DOCUMENT")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="DOCUMENTID")
    private int documentId;

    @Column(name="DOCUMENTNAME")
    private String documentName;

    @Column(name = "DOCUMENTTYPE")
    private Type documentType;

    public enum Type {
        pdf,jpeg,doc,others
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
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
}
