package com.samnart.file_upload.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoResponse {
    private Long id;
    private String originalFilename;
    private String storedFilename;
    private String contentType;
    private Long size;
    private LocalDateTime uploadTime;
    private String downloadUrl;
}
