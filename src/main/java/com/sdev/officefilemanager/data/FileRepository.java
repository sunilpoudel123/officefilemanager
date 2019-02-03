package com.sdev.officefilemanager.data;

import com.sdev.officefilemanager.domain.FileModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<FileModel, Long> {

}
