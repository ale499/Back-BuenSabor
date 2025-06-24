package com.example.MiPriApi.services.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CloudinaryValidationService {

    @Autowired
    private Cloudinary cloudinary;

    public boolean isPublicIdValid(String publicId) {
        try {
            // Obtener información del recurso en Cloudinary
            Map<String, Object> resource = cloudinary.api().resource(publicId, ObjectUtils.emptyMap());
            return resource != null; // Si el recurso existe, el publicId es válido
        } catch (Exception e) {
            // Si ocurre una excepción, el publicId no es válido
            e.printStackTrace();
            return false;
        }
    }
}
