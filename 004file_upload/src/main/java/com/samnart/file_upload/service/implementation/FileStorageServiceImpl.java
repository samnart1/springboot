package com.samnart.file_upload.service.implementation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.samnart.file_upload.config.FileStorageConfig;
import com.samnart.file_upload.dto.FileInfoResponse;
import com.samnart.file_upload.dto.FileUploadResponse;
import com.samnart.file_upload.entity.FileMetadata;
import com.samnart.file_upload.exceptions.FileStorageException;
import com.samnart.file_upload.repository.FileMetadataRepository;
import com.samnart.file_upload.service.interfaces.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    private final Path fileStorageLocation;
    private final FileStorageConfig config;
    private final FileMetadataRepository fileMetadataRepo;

    public FileStorageServiceImpl(FileStorageConfig config, FileMetadataRepository fileMetadataRepo) {
        this.config = config;
        this.fileMetadataRepo = fileMetadataRepo;
        this.fileStorageLocation = Paths.get(config.getUploadDir())
            .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
            logger.info("File storage directory created: {}", this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException(
                "Could not create the directory where the uploaded files will be stored.", ex
            );
        }

    }
    

    @Override
    public FileUploadResponse storeFile(MultipartFile file) {
        
        validateFile(file);

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(originalFilename);
        String storedFilename = generateUniqueFilename(fileExtension);

        try {
            if (originalFilename.contains("..")) {
                throw new FileStorageException("filename contains invalid path sequence " + originalFilename);
            }

            Path targetLocation = this.fileStorageLocation.resolve(storedFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            FileMetadata metadata = new FileMetadata(
                originalFilename,
                storedFilename,
                file.getContentType(),
                file.getSize()
            );

            metadata = fileMetadataRepo.save(metadata);

            String downloadUrl = "/api/files/download/" + storedFilename;

            logger.info("file uploaded successfully: {} -> {}", originalFilename, storedFilename);

            return new FileUploadResponse(
                metadata.getId(),
                metadata.getOriginalFilename(),
                metadata.getStoredFilename(),
                metadata.getContentType(),
                metadata.getSize(),
                downloadUrl,
                "FIle uploaded successfully"
            );

        } catch (IOException ex) {
            throw new FileStorageException("file couldn't be uploaded " + originalFilename, ex);
        }
    }

    @Override
    public Resource loadFileAsResource(String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("file not found " + filename);
            }

        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + filename, ex);
        }
    }

    @Override
    public List<FileInfoResponse> getAllfiles() {
        return fileMetadataRepo.findAll().stream()
            .map(this::mapToFileInfoResponse)
            .collect(Collectors.toList());
    }

    @Override
    public FileInfoResponse getFileInfo(String filename) {
        try {
            FileMetadata metadta = fileMetadataRepo.findByStoredFilename(filename)
                .orElseThrow(() -> new FileNotFoundException("file not found: " + filename));

            return mapToFileInfoResponse(metadta);

        } catch(Exception ex) {
            throw new FileNotFoundException("file not found: " + filename, ex);
        }
    }

    @Override
    public void deleteFile(String filename) {
        try {
            FileMetadata metadata = fileMetadataRepo.findByStoredFilename(filename)
                .orElseThrow(() -> new FileNotFoundException("file not found: " + filename));

            fileMetadataRepo.delete(metadata);

        } catch(IOException ex) {
            throw new FileStorageException("could not delete file " + filename, ex);
        }
    }
    
    private void validateFile(MultipartFile file) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateFile'");
    }

    private FileInfoResponse mapToFileInfoResponse(FileMetadata metadata) {
        String downloadUrl = "/api/files/download" + metadata.getStoredFilename();
        return new FileInfoResponse(
            metadata.getId(),
            metadata.getOriginalFilename(),
            metadata.getStoredFilename(),
            metadata.getContentType(),
            metadata.getSize(),
            metadata.getUploadTime(),
            downloadUrl
        );

    }
    
}
