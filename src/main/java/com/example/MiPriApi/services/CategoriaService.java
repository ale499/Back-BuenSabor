package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.Categoria;
import com.example.MiPriApi.repositories.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService extends BaseService<Categoria, Long>{
    public CategoriaService(CategoriaRepository categoriaRepository) {
        super(categoriaRepository);
    }

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public Categoria agregarSubcategoria(Long idCategoriaPadre, Categoria nuevasubCategoria) throws Exception {
        try {
            Categoria categoriaPadre = categoriaRepository.findById(idCategoriaPadre).orElse(null);
            if (categoriaPadre == null) {
                return null; // No se encontró la categoría padre
            }
            nuevasubCategoria.setCategoriaPadre(categoriaPadre); // Asigna la categoría padre
            return categoriaRepository.save(nuevasubCategoria); // Guarda la subcategoría
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public List<Categoria> listarPorCategoriaPadre(Long idCategoriaPadre) throws Exception {
        try{
            return categoriaRepository.findAllByCategoriaPadreId(idCategoriaPadre);
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public List<Categoria> listarPorSucursal(Long idSucursal)throws Exception{
        try{
            return categoriaRepository.findAllBysucursalsId(idSucursal);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Transactional
    public List<Categoria> listarCategoriasPrincipales() throws Exception {
        try {
            return categoriaRepository.findAll().stream()
                    .filter(categoria -> categoria.getCategoriaPadre() == null)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
