package com.example.MiPriApi.controllers;

import com.example.MiPriApi.services.Cloudinary.CloudinaryValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cloudinary")
public class CloudinaryController {

    @Autowired
    private CloudinaryValidationService cloudinaryValidationService;

    @GetMapping("/validatePublicId")
    public ResponseEntity<String> validatePublicId(@RequestParam String publicId) {
        boolean isValid = cloudinaryValidationService.isPublicIdValid(publicId);
        if (isValid) {
            return ResponseEntity.ok("El publicId es válido.");
        } else {
            return ResponseEntity.badRequest().body("El publicId no es válido.");
        }
    }
}
