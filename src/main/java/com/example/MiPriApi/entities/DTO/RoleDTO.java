package com.example.MiPriApi.entities.DTO;

import lombok.Data;

@Data
public class RoleDTO {
    private String name;
    private String description;
    private String Auth0RoleId;
    private Long id;
}