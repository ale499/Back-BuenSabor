package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.Producto;
import com.example.MiPriApi.repositories.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import com.example.MiPriApi.entities.DTO.ProductoDTO;

@Service
public class ProductoService extends BaseService<Producto, Long> {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        super(productoRepository);
        this.productoRepository = productoRepository;
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Nuevo método para devolver DTOs con disponibilidad
    public List<ProductoDTO> buscarPorNombreDTO(String nombre) {
        List<Producto> productos = productoRepository.findByNombreContainingIgnoreCase(nombre);
        List<ProductoDTO> resultado = new ArrayList<>();
        for (Producto producto : productos) {
            ProductoDTO dto = new ProductoDTO();
            dto.setId(producto.getId());
            dto.setNombre(producto.getNombre());
            dto.setDescripcion(producto.getDescripcion());
            dto.setPrecio(producto.getPrecio());
            boolean disponible = verificarDisponibilidad(producto); // Implementa la lógica real aquí
            dto.setDisponible(disponible);
            if (!disponible) {
                dto.setLeyenda("No disponible por el momento");
            }
            resultado.add(dto);
        }
        return resultado;
    }

    // Simulación de la lógica de disponibilidad
    private boolean verificarDisponibilidad(Producto producto) {
        // TODO: Implementar la lógica real según los insumos
        return true; // Por defecto, todos disponibles
    }
}