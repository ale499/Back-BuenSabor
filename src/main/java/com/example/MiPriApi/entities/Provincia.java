package com.example.MiPriApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Entity
@Table(name = "provincias")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Provincia extends Base{

    @Column
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "paisId")
    private Pais pais;

}
