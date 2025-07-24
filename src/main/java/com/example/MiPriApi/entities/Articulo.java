package com.example.MiPriApi.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
//metodo para diferenciar los subtipos de Articulo en el JSON
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, // Usa el nombre del tipo para identificar el subtipo
        include = JsonTypeInfo.As.PROPERTY, // Incluye el tipo como una propiedad en el JSON
        property = "type" // Nombre de la propiedad que indica el tipo
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ArticuloInsumo.class, name = "INSUMO"),
        @JsonSubTypes.Type(value = ArticuloManufacturado.class, name = "MANUFACTURADO")
})
public abstract class Articulo extends Base{

    protected String denominacion;

    @ManyToOne
    @JoinColumn(name = "categoriaId")
    protected Categoria categoria;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private Set<Image> imagenesArticulos = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "unidadMedidaId")
    protected UnidadMedida unidadMedida;

    protected Double precioVenta;
    private int tiempoPreparacion;
}
