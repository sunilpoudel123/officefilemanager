package com.sdev.officefilemanager.data;

import com.sdev.officefilemanager.domain.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends CrudRepository<Document,Long> {

    Document findDocumentByDocumentId(Long id);
}
