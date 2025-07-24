package com.example.MiPriApi.services;

import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.json.mgmt.Role;
import com.auth0.json.mgmt.RolesPage;

import com.example.MiPriApi.entities.DTO.RoleDTO;
import com.example.MiPriApi.entities.DTO.UserDTO;
import com.example.MiPriApi.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class RoleAuth0Service {

    @Autowired
    private ManagementAPI managementAPI;


    //todos los roles
    public RolesPage getAllRoles() throws Exception {
        return managementAPI.roles().list(null).execute();
    }

    //traer un rol por id
    public Role getRoleById(String roleId) throws Exception {
        return managementAPI.roles().get(roleId).execute();
    }

    //creamos un rol
    public Role createRole(RoleDTO dto) throws Exception {
        Role role = new Role();
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        return managementAPI.roles().create(role).execute();
    }
    //roles de un usuario
    public RolesPage getUserRoles(@RequestBody UserDTO userDTO) throws Exception {
        String userId = userDTO.getAuth0Id();
        return   managementAPI.users().listRoles(userId, null).execute();
    }

    //modificar role
    public Role modifyRole(RoleDTO dto) throws Exception {
        String id = dto.getAuth0RoleId();
        String name = dto.getName();
        String description = dto.getDescription();
        Role role = new Role();

        if (name != null && !name.trim().isEmpty()) {
            role.setName(name);
        }
        if (description != null && !description.trim().isEmpty()) {
            role.setDescription(description);
        }

        return managementAPI.roles().update(id, role).execute();
    }
    //eliminar role
    public void deleteRole(String id) throws Exception {
        managementAPI.roles().delete(id).execute();
    }



}