package com.example.MiPriApi.repositories;

import com.example.MiPriApi.entities.DetallePromocion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePromocionRepository extends BaseRepository<DetallePromocion, Long>{

    @Modifying
    @Transactional
    @Query("delete from DetallePromocion d where d.promocion.id = :promocionId")
    void deleteByPromocionId(@Param("promocionId") Long promocionId);
}
