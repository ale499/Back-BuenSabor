package com.example.MiPriApi.controllers;


import com.example.MiPriApi.services.Cloudinary.ImageService;
import com.example.MiPriApi.services.Cloudinary.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/images") // Mapeo raíz para todas las rutas dentro del controlador
@CrossOrigin("*") // Permite solicitudes desde cualquier origen (CORS)
public class ImageController {

    @Autowired
    private ImageService imageService; // Inyección de dependencia del servicio ImageService

    // Metodo POST para subir imágenes
    @PostMapping("/uploads")
    public ResponseEntity<String> uploadImages(
            @RequestParam(value = "uploads", required = true) MultipartFile[] files) {
        try {
            return imageService.uploadImages(files); // Llama al metodo del servicio para subir imágenes
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Manejo básico de errores, se puede mejorar para devolver una respuesta más específica
        }
    }

    // Metodo POST para eliminar imágenes por su publicId y UUID
    @PostMapping("/deleteImg")
    public ResponseEntity<String> deleteById(
            @RequestParam(value = "publicId", required = true) String publicId,
            @RequestParam(value = "uuid", required = true) String uuidString) {
        try {
            UUID uuid = UUID.fromString(uuidString); // Convierte la cadena UUID en un objeto UUID
            return imageService.deleteImage(publicId, uuid); // Llama al metodo del servicio para eliminar la imagen
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid UUID format"); // Respuesta HTTP 400 si el UUID no es válido
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred"); // Respuesta HTTP 500 si ocurre un error inesperado
        }
    }
    // Metodo POST para eliminar la primera imagen de una entidad
    @PostMapping("/deleteFirstImageFromEntity")
    public ResponseEntity<String> deleteFirstImageFromEntity(
            @RequestParam("entityId") Long entityId,
            @RequestParam("entityType") String entityType) {
        try {
            // Get images for the entity
            List<Map<String, Object>> images = imageService.getImagesByEntity(entityId, entityType).getBody();
            if (images == null || images.isEmpty()) {
                return ResponseEntity.badRequest().body("No images found for entity");
            }
            // Get first image's id and publicId
            Map<String, Object> image = images.get(0);
            UUID imageId = UUID.fromString(image.get("id").toString());
            String publicId = image.get("url").toString(); // If you store publicId separately, adjust this

            // Call delete logic
            return ((ImageServiceImpl) imageService).deleteImageFromEntity(entityId, entityType, imageId, publicId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error deleting image from entity");
        }
    }

    // Metodo GET para obtener todas las imágenes almacenadas
    @GetMapping("/getImages")
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        try {
            return imageService.getAllImages(); // Llama al metodo del servicio para obtener todas las imágenes
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Manejo básico de errores, se puede mejorar para devolver una respuesta más específica
        }
    }

    @GetMapping("/byEntity")
    public ResponseEntity<List<Map<String, Object>>> getImagesByEntity(
            @RequestParam Long entityId,
            @RequestParam String entityType) {
        return imageService.getImagesByEntity(entityId, entityType);
    }

    @PostMapping("/uploadToEntity")
    public ResponseEntity<String> uploadImageToEntity(
            @RequestParam("entityId") Long entityId,
            @RequestParam("entityType") String entityType,
            @RequestParam("file") MultipartFile file) {
        return imageService.uploadImageToEntity(entityId, entityType, file);
    }
}