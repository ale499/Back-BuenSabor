package com.example.MiPriApi.services.Cloudinary;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface CloudinaryService {

    // Metodo para subir un archivo a Cloudinary
    public default String uploadFile(MultipartFile file) {
        return null; // Implementación por defecto que retorna null
    }

    // Metodo para eliminar una imagen de Cloudinary
    public ResponseEntity<String> deleteImage(String publicId, UUID uuid);
}