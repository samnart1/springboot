package com.samnart.file_upload.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "file")
@Data
public class FileStorageConfig {
    
    private String uploadDir;
    private long maxFileSize;             // 10485760 == 10megabytes
    private List<String> allowedExtensions;
}
