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

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private UnidadMedidaRepository unidadMedidaRepository;

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
					.username("adminUser")
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
					.username("clienteUser")
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

			// Crear categorías
			Categoria categoriaComida = Categoria.builder()
					.denominacion("Comida")
					.esInsumo(false)
					.build();
			Categoria categoriaBebida = Categoria.builder()
					.denominacion("Bebida")
					.esInsumo(false)
					.build();
			Categoria categoriaInsumo = Categoria.builder()
					.denominacion("Insumo")
					.esInsumo(true)
					.build();

			categoriaRepository.save(categoriaComida);
			categoriaRepository.save(categoriaBebida);
			categoriaRepository.save(categoriaInsumo);

			System.out.println("Categorías creadas con éxito.");

			// Crear unidades de medida
			UnidadMedida unidadGramos = UnidadMedida.builder()
					.denominacion("Gramos")
					.build();
			UnidadMedida unidadLitros = UnidadMedida.builder()
					.denominacion("Litros")
					.build();
			UnidadMedida unidadUnidades = UnidadMedida.builder()
					.denominacion("Unidades")
					.build();

			unidadMedidaRepository.save(unidadGramos);
			unidadMedidaRepository.save(unidadLitros);
			unidadMedidaRepository.save(unidadUnidades);

			System.out.println("Unidades de medida creadas con éxito.");
		};


	}
}