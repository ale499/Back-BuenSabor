package com.example.MiPriApi.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unidadMedidas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UnidadMedida extends Base{

    private String denominacion;


}
