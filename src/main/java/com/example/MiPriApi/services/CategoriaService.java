package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.ArticuloInsumo;
import com.example.MiPriApi.entities.UnidadMedida;
import com.example.MiPriApi.repositories.ArticuloInsumoRepository;
import com.example.MiPriApi.entities.Categoria;
import com.example.MiPriApi.repositories.CategoriaRepository;
import com.example.MiPriApi.repositories.UnidadMedidaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService extends BaseService<Categoria, Long>{
    public CategoriaService(CategoriaRepository categoriaRepository) {
        super(categoriaRepository);
    }

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
    public List<Categoria> listarPorSucursal(Long idSucursal)throws Exception{
        try{
            return categoriaRepository.findAllBysucursalesId(idSucursal);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
}
