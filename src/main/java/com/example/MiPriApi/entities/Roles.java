package com.example.MiPriApi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Roles extends Base {
    private String description;
    private String name;
    @Column(name = "auth0_role_id")
    private String auth0RoleId;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> usuarios = new HashSet<>();
}
