package com.example.MiPriApi.entities.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String email;
    private String lastName;
    private String password;
    private String connection;
    private String name;
    private String nickName;
    private List<String> roles;
    private String auth0Id;
    private Long id;
}