package com.example.MiPriApi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import java.io.Serializable;

@Entity
@Table(name = "paises")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Pais extends Base implements Serializable {

    private String nombre;
}
