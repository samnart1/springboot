package com.samnart.file_upload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {
    private Long id;
    private String originalFilename;
    private String storedFilename;
    private String contentType;
    private Long size;
    private String downloadUrl;
    private String message;
}
