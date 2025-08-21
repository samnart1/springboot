package com.samnart.file_upload.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.samnart.file_upload.dto.FileInfoResponse;
import com.samnart.file_upload.dto.FileUploadResponse;
import com.samnart.file_upload.service.interfaces.FileStorageService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    
    private final FileStorageService service;

    public FileUploadController(FileStorageService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        
        logger.info("file upload request: ", file.getOriginalFilename());

        FileUploadResponse response = service.storeFile(file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/upload/multiple")
    public ResponseEntity<Map<String, Object>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {

        logger.info("multiple files uploads: {} files", files.length);

        Map<String, Object> response = new HashMap<>();
        List<FileUploadResponse> uploadFiles = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                FileUploadResponse uploadResponse = service.storeFile(file);
                uploadFiles.add(uploadResponse);
            } catch(Exception ex) {
                logger.error("failed to upload file", ex);
                failedFiles.add(file.getOriginalFilename() + ": " + ex.getMessage());
            }
        }

        response.put("uploadedfiles", uploadFiles);
        response.put("failed files", failedFiles);
        response.put("totalUploaded", uploadFiles.size());
        response.put("totalFailed", failedFiles.size());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request) {

        logger.info("file upload request: {}", filename);

        Resource resource = service.loadFileAsResource(filename);

        FileInfoResponse fileInfo = service.getFileInfo(filename);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        } catch (IOException ex) {
            logger.info("Could not determine file type");
        }

        if (contentType == null) {
            contentType = fileInfo.getContentType();
            if (contentType == null) {
                contentType = "application/octect-stream";
            }
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileInfo.getOriginalFilename() + "\"")
            .body(resource);
    }

    @GetMapping
    public ResponseEntity<List<FileInfoResponse>> getAllFiles() {
        logger.info("list of all files");

        List<FileInfoResponse> files = service.getAllfiles();
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping("/info/{filename:.+}")
    public ResponseEntity<FileInfoResponse> getFileInfo(@PathVariable String filename) {
        logger.info("Received file info request: {}", filename);
        FileInfoResponse fileInfo = service.getFileInfo(filename);
        return new ResponseEntity<>(fileInfo, HttpStatus.OK);
    }

    @DeleteMapping("/{filename:.+}")
    public ResponseEntity<Map<String, String>> deleteFile(@PathVariable String filename) {
        logger.info("delete request: {}", filename);

        service.deleteFile(filename);

        Map<String, String> response = new HashMap<>();
        response.put("message", "deleted successfully");
        response.put("filename", filename);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("service", "File Upload API");
        status.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
    
}
