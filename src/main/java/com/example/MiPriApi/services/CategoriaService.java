package com.example.MiPriApi.services;

<<<<<<< HEAD
import com.example.MiPriApi.entities.Categoria;
import com.example.MiPriApi.repositories.CategoriaRepository;
=======
import com.example.MiPriApi.entities.ArticuloInsumo;
import com.example.MiPriApi.entities.UnidadMedida;
import com.example.MiPriApi.repositories.ArticuloInsumoRepository;
import com.example.MiPriApi.entities.Categoria;
import com.example.MiPriApi.repositories.CategoriaRepository;
import com.example.MiPriApi.repositories.UnidadMedidaRepository;
>>>>>>> Dev
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.stream.Collectors;
>>>>>>> Dev

@Service
public class CategoriaService extends BaseService<Categoria, Long>{
    public CategoriaService(CategoriaRepository categoriaRepository) {
        super(categoriaRepository);
    }

<<<<<<< HEAD
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public Categoria agregarSubcategoria(Long idCategoriaPadre, Categoria nuevasubCategoria) throws Exception{

        try{
            Categoria cagetoriaPadre = categoriaRepository.findById(idCategoriaPadre).orElse(null);
            if (cagetoriaPadre == null){

                nuevasubCategoria.setCategoriaPadre(cagetoriaPadre);
                categoriaRepository.save(nuevasubCategoria);
                return nuevasubCategoria;
            }else{
                return null;
            }
        }catch (Exception ex){
=======


    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ArticuloInsumoRepository articuloInsumoRepository;




    public ArticuloInsumo crearInsumo(ArticuloInsumo insumo) {
        Categoria categoria = categoriaRepository.findById(insumo.getCategoria().getId()).orElseThrow();
        insumo.setCategoria(categoria);

        UnidadMedida unidadMedida = unidadMedidaRepository.findById(insumo.getUnidadMedida().getId()).orElseThrow();
        insumo.setUnidadMedida(unidadMedida);
        
        return articuloInsumoRepository.save(insumo);
    } 

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
>>>>>>> Dev
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

<<<<<<< HEAD
    @Transactional
    public List<Categoria> listarPorSucursal(Long idSucursal)throws Exception{
        try{
            return categoriaRepository.findAllBysucursalsId(idSucursal);
=======


    @Transactional
    public List<Categoria> listarPorSucursal(Long idSucursal)throws Exception{
        try{
            return categoriaRepository.findAllBysucursalesId(idSucursal);
>>>>>>> Dev
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
<<<<<<< HEAD
=======

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
>>>>>>> Dev
}
