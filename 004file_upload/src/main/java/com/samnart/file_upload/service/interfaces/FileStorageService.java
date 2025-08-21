package com.samnart.file_upload.service.interfaces;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.samnart.file_upload.dto.FileInfoResponse;
import com.samnart.file_upload.dto.FileUploadResponse;

public interface FileStorageService {
    FileUploadResponse storeFile(MultipartFile file);
    Resource loadFileAsResource(String filename);
    List<FileInfoResponse> getAllfiles();
    FileInfoResponse getFileInfo(String filename);
    void deleteFile(String filename);
}
