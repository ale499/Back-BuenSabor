package com.example.MiPriApi;

import com.example.MiPriApi.entities.*;
import com.example.MiPriApi.entities.enums.Rol;
import com.example.MiPriApi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MiPriApiApplication {

	@Autowired
	private AdministradorRepository administradorRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public static void main(String[] args) {
		SpringApplication.run(MiPriApiApplication.class, args);
		System.out.println("Servidor iniciado.");
	}

	@Bean
	CommandLineRunner initData() {
		return args -> {
			// Crear usuario para administrador
			Usuario usuarioAdmin = Usuario.builder()
					.auth0Id("auth0-admin-id")
					.userName("adminUser")
					.build();
			usuarioRepository.save(usuarioAdmin);

			// Crear administrador
			Administrador administrador = Administrador.builder()
					.nombre("Admin")
					.apellido("Principal")
					.email("admin@example.com")
					.rol(Rol.ADMIN)
					.usuario(usuarioAdmin)
					.build();
			administradorRepository.save(administrador);

			System.out.println("Administrador creado con éxito.");

			Usuario usuarioCliente = Usuario.builder()
					.auth0Id("auth0-client-id")
					.userName("clienteUser")
					.build();
			usuarioRepository.save(usuarioCliente);

			Cliente cliente = Cliente.builder()
					.nombre("Cliente")
					.apellido("Prueba")
					.email("cliente@example.com")
					.telefono("123456789")
					.fechaNacimiento("1990-01-01")
					.rol(Rol.CLIENTE)
					.usuario(usuarioCliente)
					.build();
			clienteRepository.save(cliente);

			System.out.println("Cliente de prueba creado con éxito.");

		};
	}
}