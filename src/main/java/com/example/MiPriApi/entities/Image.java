package com.example.MiPriApi.entities;

import jakarta.persistence.*;
import java.util.UUID;

import lombok.Data;

@Entity
@Data
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id; // Identificador único de la imagen

    @Column(name = "name_image")
    private String name; // Nombre de la imagen

    @Column(name = "url_image")
    private String url; // URL de la imagen en almacenamiento externo (por ejemplo, Cloudinary)
}
