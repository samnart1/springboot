package com.samnart.file_upload.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "file_metadata")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileMetadata {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Original filename cannot be blank")
    @Size(max = 255, message = "Original filename cannot exceed 255 characters")
    @Column(name = "original_filename", nullable = false)
    private String originalFilename;

    @NotBlank(message = "Stored filename cannot be blank")
    @Column(name = "stored_filename", nullable = false, unique = true)
    private String storedFilename;

    @NotBlank(message = "Content type cannot be blank")
    @Column(name = "content_type", nullable = false)
    private String contentType;

    @NotNull(message = "File size cannot be null")
    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "upload_time", nullable = false)
    private LocalDateTime uploadTime;

    @PrePersist
    protected void onCreate() {
        uploadTime = LocalDateTime.now();
    }

    public FileMetadata(String originalFilename, String storedFilename, String contentType, Long size) {
        this.originalFilename = originalFilename;
        this.storedFilename = storedFilename;
        this.contentType = contentType;
        this.size = size;
    }
}
