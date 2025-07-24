package com.example.MiPriApi.controllers.Auth0;

import com.auth0.json.mgmt.Role;

import com.example.MiPriApi.entities.DTO.RoleDTO;
import com.example.MiPriApi.entities.Roles;
import com.example.MiPriApi.services.RoleAuth0Service;
import com.example.MiPriApi.services.RoleBBDDService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/admin/roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

    private RoleAuth0Service roleService;

    private RoleBBDDService roleBBDDServicebbdd;

    public RoleController(RoleBBDDService roleBBDDServicebbdd, RoleAuth0Service roleService) {
        this.roleBBDDServicebbdd = roleBBDDServicebbdd;
        this.roleService = roleService;
    }

    @GetMapping("/getRoleByName")
    public Roles getRoleByName(@RequestParam String name) throws Exception {
        return roleBBDDServicebbdd.findByName(name);
    }

    @GetMapping
    public List<Roles> getAllRoles() throws Exception {
        return roleBBDDServicebbdd.findAll();
    }

    @GetMapping("/getRoleById")
    public Roles getRoleById(@RequestParam("id") String id) throws Exception {
        return roleBBDDServicebbdd.findById(id);
    }

    @PutMapping("/modifyRole")
    public Roles modifyRole(@RequestBody RoleDTO roleDTO) throws Exception {
        Role previousRole = null;
        try {
            // Guardar estado anterior
            previousRole = roleService.getRoleById(roleDTO.getAuth0RoleId());

            // Modificar en Auth0
            Role updatedRoleAuth0 = roleService.modifyRole(roleDTO);

            // Actualizar en BBDD
            Roles updatedRole = Roles.builder()
                    .name(updatedRoleAuth0.getName())
                    .description(updatedRoleAuth0.getDescription())
                    .auth0RoleId(updatedRoleAuth0.getId())
                    .build();
            updatedRole.setId(roleDTO.getId());

            return roleBBDDServicebbdd.update(updatedRole);
        } catch (Exception e) {
            // Revertir Auth0 si falla BBDD
            if (previousRole != null) {
                try {
                    RoleDTO rolePrev = new RoleDTO();
                    rolePrev.setAuth0RoleId(previousRole.getId());
                    rolePrev.setName(previousRole.getName());
                    rolePrev.setDescription(previousRole.getDescription());
                    rolePrev.setAuth0RoleId(previousRole.getId());

                    roleService.modifyRole(rolePrev);
                } catch (Exception ex) {
                    System.err.println("Error al revertir rol en Auth0: " + ex.getMessage());
                }
            }
            throw e;
        }
    }

    @PostMapping("/createRole")
    public Roles createRole(@RequestBody RoleDTO roleDTO) throws Exception {
        Role rolAuth = null;
        try {
            // Crear en Auth0
            rolAuth = roleService.createRole(roleDTO);

            // Guardar en BBDD
            Roles roles = Roles.builder()
                    .auth0RoleId(rolAuth.getId())
                    .description(roleDTO.getDescription())
                    .name(roleDTO.getName())
                    .build();

            return roleBBDDServicebbdd.save(roles);
        } catch (Exception e) {
            // Revertir Auth0 si falla BBDD
            if (rolAuth != null && rolAuth.getId() != null) {
                try {
                    roleService.deleteRole(rolAuth.getId());
                } catch (Exception ex) {
                    System.err.println("Error al eliminar rol en Auth0: " + ex.getMessage());
                }
            }
            throw e;
        }
    }

    @DeleteMapping("/deleteRole")
    public void deleteRole(@RequestParam("id") String id) throws Exception {
        roleBBDDServicebbdd.delete(id);
    }

    @DeleteMapping("/deleteRoleFisic")
    public void deleteRoleFisic(@RequestParam("id") String id) throws Exception {
        Roles roleToDelete = null;
        try {
            // Obtener datos del rol antes de eliminar
            roleToDelete = roleBBDDServicebbdd.findById(id);

            // Eliminar en BBDD
            roleBBDDServicebbdd.deleteFisic(id);

            // Eliminar en Auth0
            roleService.deleteRole(roleToDelete.getAuth0RoleId());
        } catch (Exception e) {
            // Si falla en Auth0, intentar restaurar en BBDD
            if (roleToDelete != null) {
                try {
                    roleBBDDServicebbdd.save(roleToDelete);
                } catch (Exception ex) {
                    System.err.println("Error al restaurar rol en BBDD: " + ex.getMessage());
                }
            }
            throw e;
        }
    }
}
