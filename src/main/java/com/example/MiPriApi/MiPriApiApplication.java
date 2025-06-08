package com.example.MiPriApi;

import com.example.MiPriApi.entities.*;
import com.example.MiPriApi.entities.enums.Rol;
import com.example.MiPriApi.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalTime;

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

	@Autowired
	private ArticuloManufacturadoRepository manufacturadoRepository;

	@Autowired
	private ArticuloInsumoRepository insumoRepository;

	@Autowired
	private ArticuloManufacturadoDetalleRepository detalleRepository;

	@Autowired
	private SucursalRepository sucursalRepository;

	@Autowired
	private DomicilioRepository domicilioRepository;

	@Autowired
	private LocalidadRepository localidadRepository;

	@Autowired
	private ProvinciaRepository provinciaRepository;

	@Autowired
	private PaisRepository paisRepository;



	public static void main(String[] args) {
		SpringApplication.run(MiPriApiApplication.class, args);
		System.out.println("Servidor iniciado.");
	}

	@Bean
	CommandLineRunner initData() {
		return args -> {

			
            // Verificar y crear usuario para administrador
            Usuario usuarioAdmin = usuarioRepository.findByUsername("adminUser")
                    .orElseGet(() -> {
                        Usuario nuevoUsuarioAdmin = Usuario.builder()
                                .auth0Id("auth0-admin-id")
                                .username("adminUser")
                                .build();
                        return usuarioRepository.save(nuevoUsuarioAdmin);
                    });

            // Verificar y crear administrador
            Administrador administrador = administradorRepository.findByEmail("admin@example.com")
                    .orElseGet(() -> {
                        Administrador nuevoAdministrador = Administrador.builder()
                                .nombre("Admin")
                                .apellido("Principal")
                                .email("admin@example.com")
                                .rol(Rol.ADMIN)
                                .usuario(usuarioAdmin)
                                .build();
                        return administradorRepository.save(nuevoAdministrador);
                    });

            System.out.println("Administrador creado con éxito.");

            // Verificar y crear usuario para cliente
            Usuario usuarioCliente = usuarioRepository.findByUsername("clienteUser")
                    .orElseGet(() -> {
                        Usuario nuevoUsuarioCliente = Usuario.builder()
                                .auth0Id("auth0-client-id")
                                .username("clienteUser")
                                .build();
                        return usuarioRepository.save(nuevoUsuarioCliente);
                    });

            // Verificar y crear cliente
            Cliente cliente = clienteRepository.findByUsuario_Username("cliente@example.com")
                    .orElseGet(() -> {
                        Cliente nuevoCliente = Cliente.builder()
                                .nombre("Cliente")
                                .apellido("Prueba")
                                .email("cliente@example.com")
                                .telefono("123456789")
                                .fechaNacimiento("1990-01-01")
                                .rol(Rol.CLIENTE)
                                .usuario(usuarioCliente)
                                .build();
                        return clienteRepository.save(nuevoCliente);
                    });

            System.out.println("Cliente de prueba creado con éxito.");

			// Crear una sucursal
			Sucursal sucursal = sucursalRepository.findByNombre("Sucursal Centro")
					.orElseGet(() -> {
						Sucursal nuevaSucursal = Sucursal.builder()
								.nombre("Sucursal Centro")
								.horarioApertura(LocalTime.of(9, 0))
								.horarioCierre(LocalTime.of(18, 0))
								.telefono("123456789")
								.email("centro@example.com")
								.build();

						// Verificar y obtener las categorías existentes
						Categoria categoriaComidaExistente = categoriaRepository.findByDenominacion("Comida").orElse(null);
						Categoria categoriaBebidaExistente = categoriaRepository.findByDenominacion("Bebida").orElse(null);

						if (categoriaComidaExistente != null) {
							nuevaSucursal.getCategorias().add(categoriaComidaExistente);
						}
						if (categoriaBebidaExistente != null) {
							nuevaSucursal.getCategorias().add(categoriaBebidaExistente);
						}

						return sucursalRepository.save(nuevaSucursal);
					});

			System.out.println("Sucursal creada con éxito.");


			// Crear domicilio
			Domicilio domicilioCentro = domicilioRepository.findByCalleAndNumeroAndPisoAndNroDpto("Av. Principal", 123, 1, 101)
					.orElseGet(() -> {
						Domicilio nuevoDomicilio = Domicilio.builder()
								.calle("Av. Principal")
								.numero(123)
								.piso(1)
								.nroDpto(101)
								.cp(5000)
								.localidad(localidadRepository.findByNombre("Ciudad Centro").orElseGet(() -> {
									Localidad nuevaLocalidad = Localidad.builder()
											.nombre("Ciudad Centro")
											.provincia(provinciaRepository.findByNombre("Provincia Ejemplo").orElseGet(() -> {
												Provincia nuevaProvincia = Provincia.builder()
														.nombre("Provincia Ejemplo")
														.pais(paisRepository.findByNombre("País Ejemplo").orElseGet(() -> {
															Pais nuevoPais = Pais.builder()
																	.nombre("País Ejemplo")
																	.build();
															return paisRepository.save(nuevoPais);
														}))
														.build();
												return provinciaRepository.save(nuevaProvincia);
											}))
											.build();
									return localidadRepository.save(nuevaLocalidad);
								}))
								.build();
						return domicilioRepository.save(nuevoDomicilio);
					});

            // Asignar domicilio a la sucursal
			Sucursal sucursalCentro = sucursalRepository.findByNombre("Sucursal Centro").orElse(null);
			if (sucursalCentro != null) {
				sucursalCentro.setDomicilio(domicilioCentro);
				sucursalRepository.save(sucursalCentro);
			}

			System.out.println("Domicilio asignado a la sucursal con éxito.");

			// Crear categorías padre
			Categoria categoriaComida = categoriaRepository.findByDenominacion("Comida")
					.orElseGet(() -> {
						Categoria nuevaCategoria = Categoria.builder()
								.denominacion("Comida")
								.esInsumo(false)
								.build();
						return categoriaRepository.save(nuevaCategoria);
					});

			Categoria categoriaBebida = categoriaRepository.findByDenominacion("Bebida")
					.orElseGet(() -> {
						Categoria nuevaCategoria = Categoria.builder()
								.denominacion("Bebida")
								.esInsumo(false)
								.build();
						return categoriaRepository.save(nuevaCategoria);
					});

			// Verificar y crear subcategorías para "Comida"
			Categoria subcategoriaPizza = categoriaRepository.findByDenominacionAndCategoriaPadre("Pizza", categoriaComida)
					.orElseGet(() -> {
						Categoria nuevaSubcategoria = Categoria.builder()
								.denominacion("Pizza")
								.esInsumo(false)
								.categoriaPadre(categoriaComida)
								.build();
						return categoriaRepository.save(nuevaSubcategoria);
					});

			Categoria subcategoriaPastas = categoriaRepository.findByDenominacionAndCategoriaPadre("Pastas", categoriaComida)
					.orElseGet(() -> {
						Categoria nuevaSubcategoria = Categoria.builder()
								.denominacion("Pastas")
								.esInsumo(false)
								.categoriaPadre(categoriaComida)
								.build();
						return categoriaRepository.save(nuevaSubcategoria);
					});

			Categoria subcategoriaEmpanadas = categoriaRepository.findByDenominacionAndCategoriaPadre("Empanadas", categoriaComida)
					.orElseGet(() -> {
						Categoria nuevaSubcategoria = Categoria.builder()
								.denominacion("Empanadas")
								.esInsumo(false)
								.categoriaPadre(categoriaComida)
								.build();
						return categoriaRepository.save(nuevaSubcategoria);
					});


			// Crear subcategorías para "Bebidas"
			Categoria subcategoriaGaseosas = categoriaRepository.findByDenominacionAndCategoriaPadre("Gaseosas", categoriaBebida)
					.orElseGet(() -> {
						Categoria nuevaSubcategoria = Categoria.builder()
								.denominacion("Gaseosas")
								.esInsumo(false)
								.categoriaPadre(categoriaBebida)
								.build();
						return categoriaRepository.save(nuevaSubcategoria);
					});

			Categoria subcategoriaJugos = categoriaRepository.findByDenominacionAndCategoriaPadre("Jugos", categoriaBebida)
					.orElseGet(() -> {
						Categoria nuevaSubcategoria = Categoria.builder()
								.denominacion("Jugos")
								.esInsumo(false)
								.categoriaPadre(categoriaBebida)
								.build();
						return categoriaRepository.save(nuevaSubcategoria);
					});

			Categoria subcategoriaCervezas = categoriaRepository.findByDenominacionAndCategoriaPadre("Cervezas", categoriaBebida)
					.orElseGet(() -> {
						Categoria nuevaSubcategoria = Categoria.builder()
								.denominacion("Cervezas")
								.esInsumo(false)
								.categoriaPadre(categoriaBebida)
								.build();
						return categoriaRepository.save(nuevaSubcategoria);
					});



			System.out.println("Categorías creadas con éxito.");

			// Crear unidades de medida
			UnidadMedida unidadGramos = unidadMedidaRepository.findByDenominacion("Gramos")
					.orElseGet(() -> {
						UnidadMedida nuevaUnidad = UnidadMedida.builder()
								.denominacion("Gramos")
								.build();
						return unidadMedidaRepository.save(nuevaUnidad);
					});

			UnidadMedida unidadLitros = unidadMedidaRepository.findByDenominacion("Litros")
					.orElseGet(() -> {
						UnidadMedida nuevaUnidad = UnidadMedida.builder()
								.denominacion("Litros")
								.build();
						return unidadMedidaRepository.save(nuevaUnidad);
					});

			UnidadMedida unidadUnidades = unidadMedidaRepository.findByDenominacion("Unidades")
					.orElseGet(() -> {
						UnidadMedida nuevaUnidad = UnidadMedida.builder()
								.denominacion("Unidades")
								.build();
						return unidadMedidaRepository.save(nuevaUnidad);
					});

			System.out.println("Unidades de medida creadas con éxito.");

			// Crear artículos insumos
			ArticuloInsumo manteca = insumoRepository.findByDenominacion("Manteca")
					.orElseGet(() -> {
						ArticuloInsumo nuevoInsumo = ArticuloInsumo.builder()
								.denominacion("Manteca")
								.unidadMedida(unidadMedidaRepository.findByDenominacion("Gramos").orElse(null))
								.categoria(categoriaRepository.findByDenominacion("Comida").orElse(null)) // Asignar categoría
								.precioCompra(80.0)
								.precioVenta(100.0) // Asignar precio de venta
								.stockActual(50)
								.stockMaximo(100)
								.stockMinimo(10)
								.esParaElaborar(true)
								.build();
						return insumoRepository.save(nuevoInsumo);
					});
			ArticuloInsumo harina = insumoRepository.findByDenominacion("Harina")
					.orElseGet(() -> {
						ArticuloInsumo nuevoInsumo = ArticuloInsumo.builder()
								.denominacion("Harina")
								.unidadMedida(unidadMedidaRepository.findByDenominacion("Gramos").orElse(null))
								.categoria(categoriaRepository.findByDenominacion("Comida").orElse(null)) // Asignar categoría
								.precioCompra(50.0)
								.precioVenta(70.0) // Asignar precio de venta
								.stockActual(100)
								.stockMaximo(200)
								.stockMinimo(20)
								.esParaElaborar(true)
								.build();
						return insumoRepository.save(nuevoInsumo);
					});
			ArticuloInsumo azucar = insumoRepository.findByDenominacion("Azúcar")
					.orElseGet(() -> {
						ArticuloInsumo nuevoInsumo = ArticuloInsumo.builder()
								.denominacion("Azúcar")
								.unidadMedida(unidadMedidaRepository.findByDenominacion("Gramos").orElse(null))
								.categoria(categoriaRepository.findByDenominacion("Comida").orElse(null)) // Asignar categoría
								.precioCompra(40.0)
								.precioVenta(50.0) // Asignar precio de venta
								.stockActual(150)
								.stockMaximo(300)
								.stockMinimo(30)
								.esParaElaborar(false)
								.build();
						return insumoRepository.save(nuevoInsumo);
					});

			ArticuloInsumo sal = insumoRepository.findByDenominacion("Sal")
					.orElseGet(() -> {
						ArticuloInsumo nuevoInsumo = ArticuloInsumo.builder()
								.denominacion("Sal")
								.unidadMedida(unidadMedidaRepository.findByDenominacion("Gramos").orElse(null))
								.categoria(categoriaRepository.findByDenominacion("Comida").orElse(null)) // Asignar categoría
								.precioCompra(10.0)
								.precioVenta(15.0) // Asignar precio de venta
								.stockActual(200)
								.stockMaximo(400)
								.stockMinimo(50)
								.esParaElaborar(false)
								.build();
						return insumoRepository.save(nuevoInsumo);
					});


			System.out.println("Artículos insumos creados con éxito.");


			// Crear artículo manufacturado
			ArticuloManufacturado pizza = manufacturadoRepository.findByDenominacion("Pizza")
					.orElseGet(() -> {
						ArticuloManufacturado nuevoManufacturado = ArticuloManufacturado.builder()
								.denominacion("Pizza")
								.descripcion("Pizza de mozzarella")
								.tiempoEstimadoMinutos(30)
								.preparacion("Hornear la masa y agregar ingredientes")
								.categoria(categoriaRepository.findByDenominacion("Comida").orElse(null)) // Asignar categoría
								.precioVenta(500.0) // Asignar precio de venta
								.build();
						return manufacturadoRepository.save(nuevoManufacturado);
					});

			// Crear detalles del artículo manufacturado
			ArticuloManufacturadoDetalle detalleHarina = detalleRepository.findByArticuloManufacturadoAndArticuloInsumo(pizza, insumoRepository.findByDenominacion("Harina").orElse(null))
					.orElseGet(() -> {
						ArticuloManufacturadoDetalle nuevoDetalle = ArticuloManufacturadoDetalle.builder()
								.articuloManufacturado(pizza)
								.articuloInsumo(insumoRepository.findByDenominacion("Harina").orElse(null))
								.cantidad(500) // Cantidad en gramos
								.build();
						return detalleRepository.save(nuevoDetalle);
					});

			ArticuloManufacturadoDetalle detalleManteca = detalleRepository.findByArticuloManufacturadoAndArticuloInsumo(pizza, insumoRepository.findByDenominacion("Manteca").orElse(null))
					.orElseGet(() -> {
						ArticuloManufacturadoDetalle nuevoDetalle = ArticuloManufacturadoDetalle.builder()
								.articuloManufacturado(pizza)
								.articuloInsumo(insumoRepository.findByDenominacion("Manteca").orElse(null))
								.cantidad(100) // Cantidad en gramos
								.build();
						return detalleRepository.save(nuevoDetalle);
					});

			System.out.println("Artículo manufacturado 'Pizza' creado con éxito.");






		};

	}
}

