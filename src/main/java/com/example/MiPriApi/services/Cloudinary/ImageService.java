package com.example.MiPriApi.services.Cloudinary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface ImageService {


    ResponseEntity<List<Map<String, Object>>> getAllImages();
    ResponseEntity<String> uploadImages(MultipartFile[] files);
    ResponseEntity<String> deleteImage(String publicId, UUID uuid);
    ResponseEntity<String> uploadImageToEntity(Long entityId, String entityType, MultipartFile file);
    ResponseEntity<List<Map<String, Object>>> getImagesByEntity(Long entityId, String entityType);



}
