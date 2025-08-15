package com.example.MiPriApi.controllers.Auth0;


import com.example.MiPriApi.entities.DTO.AssingRoleDTO;
import com.example.MiPriApi.entities.DTO.UserDTO;
import com.example.MiPriApi.entities.Roles;
import com.example.MiPriApi.entities.User;
import com.example.MiPriApi.repositories.RoleRepository;
import com.example.MiPriApi.services.UserAuth0Service;
import com.example.MiPriApi.services.UserBBDDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@RestController
@RequestMapping(path = "/api/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserAuth0Service userAuth0Service;
    private final UserBBDDService userBBDDService;
    private final RoleRepository roleRepository;

    public UserController(UserAuth0Service userAuth0Service, RoleRepository roleRepository, UserBBDDService userBBDDService) {
        this.userAuth0Service = userAuth0Service;
        this.roleRepository = roleRepository;
        this.userBBDDService = userBBDDService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userBBDDService.findAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener los usuarios: " + e.getMessage());
        }
    }

    @PostMapping("/getUserById")
    public ResponseEntity<?> getUserById(@RequestBody UserDTO userDTO) {
        try {
            // Decode Auth0 ID if it contains encoded characters
            String decodedAuth0Id = URLDecoder.decode(userDTO.getAuth0Id(), StandardCharsets.UTF_8);
            User user = userBBDDService.findById(decodedAuth0Id);
            if(user == null) {
                return ResponseEntity.ok(false);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado: " + e.getMessage());
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            com.auth0.json.mgmt.users.User newUser = userAuth0Service.createUser(userDTO);
            userAuth0Service.assignRoles(newUser.getId(), userDTO.getRoles());

            Set<Roles> rolesAsignados = userDTO.getRoles().stream()
                    .map(idRol -> roleRepository.findByAuth0RoleId(idRol)
                            .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + idRol)))
                    .collect(Collectors.toSet());

            User userBBDD = User.builder()
                    .auth0Id(newUser.getId())
                    .name(newUser.getName())
                    .roles(rolesAsignados)
                    .nickName(userDTO.getNickName())
                    .userEmail(newUser.getEmail())
                    .build();

            return ResponseEntity.ok(userBBDDService.save(userBBDD));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear el usuario: " + e.getMessage());
        }
    }

    @PostMapping("/createUserClient")
    public ResponseEntity<?> createUserClient(@RequestBody UserDTO userDTO) {
        try {
            // Decode Auth0 ID if it contains encoded characters
            String decodedAuth0Id = URLDecoder.decode(userDTO.getAuth0Id(), StandardCharsets.UTF_8);
            com.auth0.json.mgmt.users.User userAuth0 = userAuth0Service.getUserById(decodedAuth0Id);
            if(userAuth0 == null) {
                return ResponseEntity.internalServerError().body("El usuario no existe");
            }

            userAuth0Service.assignRoles(userAuth0.getId(), userDTO.getRoles());

            Set<Roles> rolesAsignados = userDTO.getRoles().stream()
                    .map(idRol -> roleRepository.findByAuth0RoleId(idRol)
                            .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + idRol)))
                    .collect(Collectors.toSet());

            User userBBDD = User.builder()
                    .auth0Id(userAuth0.getId())
                    .name(userAuth0.getName())
                    .roles(rolesAsignados)
                    .nickName(userDTO.getNickName())
                    .userEmail(userAuth0.getEmail())
                    .build();

            return ResponseEntity.ok(userBBDDService.save(userBBDD));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear cliente: " + e.getMessage());
        }
    }

    @PutMapping("/modifyUser")
    public ResponseEntity<?> modifyUser(@RequestBody UserDTO userDTO) {
        try {
            // Decode Auth0 ID if it contains encoded characters
            String decodedAuth0Id = URLDecoder.decode(userDTO.getAuth0Id(), StandardCharsets.UTF_8);
            userDTO.setAuth0Id(decodedAuth0Id);

            com.auth0.json.mgmt.users.User newUser = userAuth0Service.modifyUser(userDTO);

            Set<Roles> rolesAsignados = userDTO.getRoles().stream()
                    .map(idRol -> roleRepository.findByAuth0RoleId(idRol)
                            .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + idRol)))
                    .collect(Collectors.toSet());

            User updatedUser = User.builder()
                    .auth0Id(newUser.getId())
                    .name(newUser.getName())
                    .userEmail(newUser.getEmail())
                    .nickName(userDTO.getNickName())
                    .roles(rolesAsignados)
                    .lastName(userDTO.getLastName())
                    .build();
            updatedUser.setId(userDTO.getId());

            return ResponseEntity.ok(userBBDDService.update(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al modificar usuario: " + e.getMessage());
        }
    }
    // Actualizar contraseña de un Cliente
    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestParam String userId, @RequestParam String newPassword) {
        try {
            // Decode Auth0 ID if it contains encoded characters
            String decodedUserId = URLDecoder.decode(userId, StandardCharsets.UTF_8);
            userAuth0Service.updatePassword(decodedUserId, newPassword);
            return ResponseEntity.ok("Contraseña actualizada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar la contraseña: " + e.getMessage());
        }
    }



    @DeleteMapping("/deleteUserById")
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO userDTO) {
        try {
            // Decode Auth0 ID if it contains encoded characters
            String decodedAuth0Id = URLDecoder.decode(userDTO.getAuth0Id(), StandardCharsets.UTF_8);
            userBBDDService.delete(decodedAuth0Id);
            return ResponseEntity.ok("Usuario eliminado (lógico) correctamente.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al eliminar usuario: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteUserByIdFisic")
    public ResponseEntity<?> deleteUserFisic(@RequestBody UserDTO userDTO) {
        try {
            // Decode Auth0 ID if it contains encoded characters
            String decodedAuth0Id = URLDecoder.decode(userDTO.getAuth0Id(), StandardCharsets.UTF_8);
            userBBDDService.deleteFisic(decodedAuth0Id);
            userAuth0Service.deleteUser(decodedAuth0Id);
            return ResponseEntity.ok("Usuario eliminado físicamente.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al eliminar físicamente al usuario: " + e.getMessage());
        }
    }

    @PostMapping("/addRolesUser")
    public ResponseEntity<?> assignRoles(@RequestBody AssingRoleDTO request) {
        try {
            // Decode Auth0 ID if it contains encoded characters
            String decodedId = URLDecoder.decode(request.getId(), StandardCharsets.UTF_8);
            userAuth0Service.assignRoles(decodedId, request.getRoles());
            User user = userBBDDService.findById(decodedId);

            Set<Roles> rolesAAgregar = request.getRoles().stream()
                    .map(idRol -> roleRepository.findByAuth0RoleId(idRol)
                            .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + idRol)))
                    .collect(Collectors.toSet());

            user.getRoles().addAll(rolesAAgregar);
            return ResponseEntity.ok(userBBDDService.update(user));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al agregar roles: " + e.getMessage());
        }
    }

    @GetMapping("/clients")
    public ResponseEntity<List<com.auth0.json.mgmt.users.User>> getAllClients() {
        try {
            // Buscar el ID del rol "Client" dinámicamente
            String clientRoleId = userAuth0Service.getRoleIdByName("client");
            if (clientRoleId == null) {
                return ResponseEntity.badRequest().body(null);
            }

            List<com.auth0.json.mgmt.users.User> clients = userAuth0Service.getUsersByRole(clientRoleId);
            return ResponseEntity.ok(clients);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/removeRolesUser")
    public ResponseEntity<?> removeRoles(@RequestBody AssingRoleDTO request) {
        try {
            // Decode Auth0 ID if it contains encoded characters
            String decodedId = URLDecoder.decode(request.getId(), StandardCharsets.UTF_8);
            userAuth0Service.removeRoles(decodedId, request.getRoles());
            User user = userBBDDService.findById(decodedId);

            Set<Roles> rolesAEliminar = request.getRoles().stream()
                    .map(idRol -> roleRepository.findByAuth0RoleId(idRol)
                            .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + idRol)))
                    .collect(Collectors.toSet());

            user.getRoles().removeAll(rolesAEliminar);
            return ResponseEntity.ok(userBBDDService.update(user));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al quitar roles: " + e.getMessage());
        }
    }
}
