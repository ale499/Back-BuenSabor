package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.Promocion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromocionRepository extends BaseRepository<Promocion, Long>{

    List<Promocion> findAllBySucursalesId(Long sucursalId);}
