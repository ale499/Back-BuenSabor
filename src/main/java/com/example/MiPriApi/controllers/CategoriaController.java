package com.example.MiPriApi.controllers;

import com.example.MiPriApi.entities.Categoria;
import com.example.MiPriApi.services.BaseService;
import com.example.MiPriApi.services.CategoriaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/categoria")
public class CategoriaController extends BaseController<Categoria, Long>{
    public CategoriaController(CategoriaService service) {
        super(service);
    }

    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping("/subcategoria/{idCP}")
    public Optional<Categoria> agregarSubcategoria(@PathVariable Long id_CP, @RequestBody Categoria subCategoria) throws Exception {
        Categoria catPadre= categoriaService.agregarSubcategoria(id_CP, subCategoria);
        return Optional.ofNullable(catPadre);
    }

    @RequestMapping("/categoriaPadre/{id}")
    public ResponseEntity <List<Categoria>> listarPorCategoriaPadre(@PathVariable Long id) throws Exception {
        List<Categoria> categorias = categoriaService.listarPorCategoriaPadre(id);
        return ResponseEntity.ok(categorias);
    }

    @RequestMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<Categoria>> listarPorSucursal(@PathVariable Long idSucursal) throws Exception{
        List<Categoria> categorias = categoriaService.listarPorSucursal(idSucursal);
        return ResponseEntity.ok(categorias);
    }
}
