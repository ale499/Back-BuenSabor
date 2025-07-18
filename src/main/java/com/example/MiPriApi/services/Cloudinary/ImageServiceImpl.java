package com.example.MiPriApi.services.Cloudinary;

import com.example.MiPriApi.entities.*;
import com.example.MiPriApi.repositories.*;
import com.example.MiPriApi.services.Cloudinary.CloudinaryService;
import com.example.MiPriApi.services.Cloudinary.ImageService;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;




import java.util.*;


@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private CloudinaryService cloudinaryService; // Servicio para interactuar con Cloudinary
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PromocionRepository promocionRepository;

    @Autowired
    private ArticuloManufacturadoRepository articuloManufacturadoRepository;

    @Autowired
    private ArticuloInsumoRepository articuloInsumoRepository;

    // Metodo para obtener todas las imágenes almacenadas
    @Override
    public ResponseEntity<List<Map<String, Object>>> getAllImages() {
        try {
            // Consultar todas las imágenes desde la base de datos
            List<Image> images = imageRepository.findAll();
            List<Map<String, Object>> imageList = new ArrayList<>();

            // Convertir cada imagen en un mapa de atributos para devolver como JSON
            for (Image image : images) {
                Map<String, Object> imageMap = new HashMap<>();
                imageMap.put("id", image.getId());
                imageMap.put("name", image.getName());
                imageMap.put("url", image.getUrl());
                imageList.add(imageMap);
            }

            // Devolver la lista de imágenes como ResponseEntity con estado OK (200)
            return ResponseEntity.ok(imageList);
        } catch (Exception e) {
            e.printStackTrace();
            // Devolver un error interno del servidor (500) si ocurre alguna excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Metodo para subir imágenes a Cloudinary y guardar los detalles en la base de datos
    @Override
    public ResponseEntity<String> uploadImages(MultipartFile[] files) {
        List<String> urls = new ArrayList<>();

        try {
            // Iterar sobre cada archivo recibido
            for (MultipartFile file : files) {
                // Verificar si el archivo está vacío
                if (file.isEmpty()) {
                    return ResponseEntity.badRequest().build();
                }

                // Crear una entidad Image y establecer su nombre y URL (subida a Cloudinary)
                Image image = new Image();
                image.setName(file.getOriginalFilename()); // Establecer el nombre del archivo original
                image.setUrl(cloudinaryService.uploadFile(file)); // Subir el archivo a Cloudinary y obtener la URL

                // Verificar si la URL de la imagen es nula (indicativo de fallo en la subida)
                if (image.getUrl() == null) {
                    return ResponseEntity.badRequest().build();
                }

                // Guardar la entidad Image en la base de datos
                imageRepository.save(image);

                // Agregar la URL de la imagen a la lista de URLs subidas
                urls.add(image.getUrl());
            }

            // Convertir la lista de URLs a un objeto JSON y devolver como ResponseEntity con estado OK (200)
            return new ResponseEntity<>("{\"status\":\"OK\", \"urls\":" + urls + "}", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            // Devolver un error (400) si ocurre alguna excepción durante el proceso de subida
            return new ResponseEntity<>("{\"status\":\"ERROR\", \"message\":\"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }
    }

    // Metodo para eliminar una imagen por su identificador en la base de datos y en Cloudinary
    @Transactional
    public ResponseEntity<String> deleteImage(String publicId, UUID idBd) {
        try {
            // Buscar los ArticuloInsumo que contienen la imagen
            List<ArticuloInsumo> articulosInsumo = articuloInsumoRepository.findAll();
            for (ArticuloInsumo insumo : articulosInsumo) {
                insumo.getImagenesArticulos().removeIf(image -> image.getId().equals(idBd));
                articuloInsumoRepository.save(insumo); // Guardar los cambios
            }

            // Eliminar la imagen de la base de datos
            imageRepository.deleteById(idBd);

            // Eliminar la imagen de Cloudinary
            return cloudinaryService.deleteImage(publicId, idBd);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("{\"status\":\"ERROR\", \"message\":\"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> uploadImageToEntity(Long entityId, String entityType, MultipartFile file) {
        try {
            // Subir la imagen a Cloudinary
            Image image = new Image();
            image.setName(file.getOriginalFilename());
            image.setUrl(cloudinaryService.uploadFile(file));

            // Guardar la imagen en la base de datos
            imageRepository.save(image);

            // Asociar la imagen a la entidad correspondiente
            switch (entityType.toLowerCase()) {
                case "administrador":
                    Administrador admin = administradorRepository.findById(entityId)
                            .orElseThrow(() -> new Exception("Administrador no encontrado"));
                    admin.setImagen(image);
                    administradorRepository.save(admin);
                    break;
                case "cliente":
                    Cliente cliente = clienteRepository.findById(entityId)
                            .orElseThrow(() -> new Exception("Cliente no encontrado"));
                    cliente.setImagen(image);
                    clienteRepository.save(cliente);
                    break;
                case "empleado":
                    Empleado empleado = empleadoRepository.findById(entityId)
                            .orElseThrow(() -> new Exception("Empleado no encontrado"));
                    empleado.setImagen(image);
                    empleadoRepository.save(empleado);
                    break;
                case "promocion":
                    Promocion promocion = promocionRepository.findById(entityId)
                            .orElseThrow(() -> new Exception("Promoción no encontrada"));
                    promocion.getImagenesPromocion().add(image);
                    promocionRepository.save(promocion);
                    break;
                case "insumo":
                    ArticuloInsumo insumo = articuloInsumoRepository.findById(entityId)
                            .orElseThrow(() -> new Exception("Articulo Insumo no encontrado"));
                    insumo.getImagenesArticulos().add(image);
                    articuloInsumoRepository.save(insumo);
                    break;
                case "manufacturado":
                    ArticuloManufacturado manufacturado = articuloManufacturadoRepository.findById(entityId)
                            .orElseThrow(() -> new Exception("Articulo Manufacturado no encontrado"));
                    manufacturado.getImagenesArticulos().add(image);
                    articuloManufacturadoRepository.save(manufacturado);
                    break;
                default:
                    throw new Exception("Tipo de entidad no soportado");
            }

            return ResponseEntity.ok("Imagen subida y asociada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al asociar la imagen");
        }
    }

    @Override
    public ResponseEntity<List<Map<String, Object>>> getImagesByEntity(Long entityId, String entityType) {
        List<Map<String, Object>> imageList = new ArrayList<>();
        try {
            List<Image> images = new ArrayList<>();
            switch (entityType.toLowerCase()) {
                case "insumo":
                    ArticuloInsumo insumo = articuloInsumoRepository.findById(entityId)
                            .orElseThrow(() -> new Exception("Articulo Insumo not found"));
                    images = new ArrayList<>(insumo.getImagenesArticulos());
                    break;
                case "manufacturado":
                    ArticuloManufacturado manufacturado = articuloManufacturadoRepository.findById(entityId)
                            .orElseThrow(() -> new Exception("Articulo Manufacturado not found"));
                    images = new ArrayList<>(manufacturado.getImagenesArticulos());
                    break;
                case "promocion":
                    Promocion promocion = promocionRepository.findById(entityId)
                            .orElseThrow(() -> new Exception("Promocion not found"));
                    images = new ArrayList<>(promocion.getImagenesPromocion());
                    break;
                case "administrador":
                    Administrador admin = administradorRepository.findById(entityId)
                            .orElseThrow(() -> new Exception("Administrador not found"));
                    if (admin.getImagen() != null) images.add(admin.getImagen());
                    break;
                case "cliente":
                    Cliente cliente = clienteRepository.findById(entityId)
                            .orElseThrow(() -> new Exception("Cliente not found"));
                    if (cliente.getImagen() != null) images.add(cliente.getImagen());
                    break;
                case "empleado":
                    Empleado empleado = empleadoRepository.findById(entityId)
                            .orElseThrow(() -> new Exception("Empleado not found"));
                    if (empleado.getImagen() != null) images.add(empleado.getImagen());
                    break;
                default:
                    throw new Exception("Unsupported entity type");
            }

            for (Image image : images) {
                Map<String, Object> imageMap = new HashMap<>();
                imageMap.put("id", image.getId());
                imageMap.put("name", image.getName());
                imageMap.put("url", image.getUrl());
                imageList.add(imageMap);
            }
            return ResponseEntity.ok(imageList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}