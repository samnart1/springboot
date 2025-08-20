package com.samnart.file_upload.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samnart.file_upload.entity.FileMetadata;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    Optional<FileMetadata> findByStoredFilename(String storedFilename);
    boolean existsByStoredFilename(String storedFilename);
}
