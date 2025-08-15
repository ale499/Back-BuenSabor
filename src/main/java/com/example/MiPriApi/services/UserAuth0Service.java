package com.example.MiPriApi.services;

import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.json.mgmt.users.User;
import com.auth0.json.mgmt.users.UsersPage;


import com.example.MiPriApi.entities.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuth0Service {


    @Autowired
    private ManagementAPI managementAPI;

    //traer los usuarios
    public UsersPage getAllUsers() throws Exception {
        return managementAPI.users().list(null).execute();
    }
    //traer usuariosPorId
    public User getUserById(String id) throws Exception {
        return managementAPI.users().get(id, null).execute();
    }
    //modificar un usuario
    public User modifyUser(UserDTO userDTO) throws Exception {
        String userId = userDTO.getAuth0Id();
        User userUpdate = new User();

        if (userDTO.getName() != null && !userDTO.getName().isEmpty()) {
            userUpdate.setName(userDTO.getName());
        }
        if(userDTO.getNickName() != null && !userDTO.getNickName().isEmpty()) {
            userUpdate.setNickname(userDTO.getNickName());
        }
        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
            userUpdate.setEmail(userDTO.getEmail());
        }

        return managementAPI.users().update(userId, userUpdate).execute();
    }

    //creamos  un usuario
    public User createUser(UserDTO dto) throws Exception {
        User user = new User(dto.getConnection());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setEmailVerified(true);

        return managementAPI.users().create(user).execute();
    }

    // Metodo para actualizar la contraseña de un usuario
    public void updatePassword(String userId, String newPassword) throws Exception {
        User userUpdate = new User();
        userUpdate.setPassword(newPassword);
        managementAPI.users().update(userId, userUpdate).execute();
    }

    //eliminamos un usuario
    public void deleteUser(String id) throws Exception {
        managementAPI.users().delete(id).execute();
    }

    // Método para obtener usuarios que tienen un rol específico
    public List<User> getUsersByRole(String roleId) throws Exception {
        // Buscar usuarios con el rol específico
        return managementAPI.roles().listUsers(roleId, null).execute().getItems();
    }

    //asignamos roles a un usuario
    public void assignRoles(String userId, List<String> roleIds) throws Exception {
        managementAPI.users().addRoles(userId, roleIds).execute();
    }
    //removemosRoles de un usuario
    public void removeRoles(String userId, List<String> roleIds) throws Exception {
        managementAPI.users().removeRoles(userId, roleIds).execute();
    }

    // Método para obtener el ID de un rol por su nombre
    public String getRoleIdByName(String roleName) throws Exception {
        return managementAPI.roles().list(null).execute().getItems().stream()
                .filter(role -> roleName.equalsIgnoreCase(role.getName()))
                .findFirst()
                .map(role -> role.getId())
                .orElse(null);
    }
}