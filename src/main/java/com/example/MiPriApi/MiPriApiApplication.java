package com.example.MiPriApi;

import com.example.MiPriApi.entities.*;
import com.example.MiPriApi.entities.enums.*;
import com.example.MiPriApi.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class MiPriApiApplication {

	@Autowired
	private ImagenRepository imagenRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private EmpleadoRepository empleadoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PaisRepository paisRepository;
	@Autowired
	private ProvinciaRepository provinciaRepository;
	@Autowired
	private LocalidadRepository localidadRepository;
	@Autowired
	private DomicilioRepository domicilioRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private SucursalRepository sucursalRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private DetallePedidoRepository detallePedidoRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private PromocionRepository promocionRepository;
	@Autowired
	private UnidadMedidaRepository unidadMedidaRepository;
	@Autowired
	private ArticuloInsumoRepository articuloInsumoRepository;
	@Autowired
	private ArticuloManufacturadoRepository articuloManufacturadoRepository;
	@Autowired
	private ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;
	@Autowired
	private DetallePromocionRepository promocionDetalleRepository;

	public static void main(String[] args) {
		SpringApplication.run(MiPriApiApplication.class, args);
		System.out.println("Servidor iniciado.");
	}

	@Bean
	@Transactional
	CommandLineRunner init(ImagenRepository imagenRepository,
						   UsuarioRepository usuarioRepository,
						   EmpleadoRepository empleadoRepository,
						   ClienteRepository clienteRepository,
						   PaisRepository paisRepository,
						   ProvinciaRepository provinciaRepository,
						   LocalidadRepository localidadRepository,
						   DomicilioRepository domicilioRepository,
						   EmpresaRepository empresaRepository,
						   SucursalRepository sucursalRepository,
						   PedidoRepository pedidoRepository,
						   DetallePedidoRepository detallePedidoRepository,
						   CategoriaRepository categoriaRepository,
						   PromocionRepository promocionRepository,
						   UnidadMedidaRepository unidadMedidaRepository,
						   ArticuloInsumoRepository articuloInsumoRepository,
						   ArticuloManufacturadoRepository articuloManufacturadoRepository,
						   ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository,
						   FacturaRepository facturaRepository){
		return args -> {

			Pais pais1 = Pais.builder()
					.nombre("Pais 1")
					.build();
			paisRepository.save(pais1);

			Provincia prov1 = Provincia.builder()
					.nombre("Provincia 1")
					.pais(pais1)
					.build();
			provinciaRepository.save(prov1);

			Localidad loc1 = Localidad.builder()
					.nombre("Localidad 1")
					.provincia(prov1)
					.build();
			localidadRepository.save(loc1);

			Domicilio dom1 = Domicilio.builder()
					.calle("Calle 1")
					.numero(1234)
					.cp(1234)
					.localidad(loc1)
					.build();
			domicilioRepository.save(dom1);

			Domicilio dom2 = Domicilio.builder()
					.calle("Calle 2")
					.numero(5678)
					.cp(5678)
					.localidad(loc1)
					.build();
			domicilioRepository.save(dom2);

			Empresa empr1 = Empresa.builder()
					.razonSocial("Empresa S.A")
					.nombre("Empresa 1")
					.cuil(12334555)
					.build();
			empresaRepository.save(empr1);

			Sucursal suc1 = Sucursal.builder()
					.nombre("Sucursal 1")
					.casaMatriz(Boolean.FALSE)
					.domicilio(dom1)
					.horarioApertura(LocalTime.of(8,30))
					.horarioCierre(LocalTime.of(20,30))
					.empresa(empr1)
					.build();
			sucursalRepository.save(suc1);

			Imagen img1 = Imagen.builder()
					.denominacion("Imangen 1")
					.build();
			imagenRepository.save(img1);
			Imagen img2 = Imagen.builder()
					.denominacion("Imangen 2")
					.build();
			imagenRepository.save(img2);

			Imagen img3 = Imagen.builder()
					.denominacion("Imangen 3")
					.build();
			imagenRepository.save(img3);

			Imagen img4 = Imagen.builder()
					.denominacion("Imangen 4")
					.build();
			imagenRepository.save(img4);

			Imagen img5 = Imagen.builder()
					.denominacion("Imangen 5")
					.build();
			imagenRepository.save(img5);

			Imagen img6 = Imagen.builder()
					.denominacion("Imangen 5")
					.build();
			imagenRepository.save(img6);

			Usuario us1 = Usuario.builder()
					.auth0Id("pass")
					.userName("user")
					.build();
			usuarioRepository.save(us1);
			Usuario us2 = Usuario.builder()
					.auth0Id("pass2")
					.userName("user2")
					.build();
			usuarioRepository.save(us2);

			Empleado em1 = Empleado.builder()
					.sucursal(suc1)
					.build();
			em1.setNombre("Cinthia");
			em1.setApellido("Rigoni");
			em1.setTelefono("123456");
			em1.setEmail("prueba@gmail.com");
			em1.setFechaNacimiento("1992-05-27");
			em1.setImagenPersona(img1);
			em1.setImagen(img5);
			em1.setUsuario(us1);
			em1.setRol(Rol.CAJERO);
			empleadoRepository.save(em1);

			Cliente cli1 = Cliente.builder().build();
			cli1.setNombre("Juan");
			cli1.setApellido("Fernandez");
			cli1.setTelefono("456789");
			cli1.setEmail("probando@gmail.com");
			cli1.setFechaNacimiento("1996-01-31");
			cli1.setImagenPersona(img2);
			cli1.setUsuario(us2);
			cli1.setRol(Rol.CLIENTE);
			cli1.getDomicilios().add(dom1);
			cli1.getDomicilios().add(dom2);
			cli1.setImagen(img6);
			clienteRepository.save(cli1);

			Pedido ped1 = Pedido.builder()
					.fechaPedido(LocalDate.of(2023,05,23))
					.sucursal(suc1).cliente(cli1)
					.empleado(em1).total(250.5)
					.domicilio(dom1)
					.estado(Estado.PENDIENTE)
					.formaPago(FormaPago.EFECTIVO)
					.horaEstimadaFinalizacion(LocalTime.of(12,55))
					.tipoEnvio(TipoEnvio.DELIVERY)
					.totalCosto(170.0)
					.build();
			pedidoRepository.save(ped1);


			Categoria cat1 = Categoria.builder()
					.denominacion("Categoria 1")
					.build();
			categoriaRepository.save(cat1);


			Categoria subCat1 = Categoria.builder()
					.denominacion("Subcategoria 1")
					.categoriaPadre(cat1)
					.build();
			categoriaRepository.save(subCat1);

			Categoria subCat2 = Categoria.builder()
					.denominacion("Subcategoria 2")
					.categoriaPadre(cat1)
					.build();
			categoriaRepository.save(subCat2);

			cat1.getSubcategorias().add(subCat1);
			cat1.getSubcategorias().add(subCat2);
			categoriaRepository.save(cat1);

			suc1.getCategorias().add(cat1);

			Imagen imgProm1 = Imagen.builder()
					.denominacion("Imangen promo 1")
					.build();
			imagenRepository.save(imgProm1);
			Imagen imgProm2 = Imagen.builder()
					.denominacion("Imangen promo 2")
					.build();
			imagenRepository.save(imgProm2);

			Promocion prom1 = Promocion.builder()
					.denominacion("Promocion 1")
					.descripcionDescuento("10% de descuento")
					.fechaDesde(LocalDate.of(2024,06,12))
					.fechaHasta(LocalDate.of(2024, 06, 25))
					.horaDesde(LocalTime.of(20,30))
					.horaHasta(LocalTime.of(23,30))
					.precioPromocional(2500.5)
					.tipoPromocion(TipoPromocion.HAPPYHOUR)
					.build();
			prom1.getImagenesPromocion().add(imgProm1);
			prom1.getSucursals().add(suc1);
			promocionRepository.save(prom1);

			Promocion prom2 = Promocion.builder()
					.denominacion("Promocion 2")
					.descripcionDescuento("15% de descuento")
					.fechaDesde(LocalDate.of(2024,06,12))
					.fechaHasta(LocalDate.of(2024, 06, 25))
					.horaDesde(LocalTime.of(11,30))
					.horaHasta(LocalTime.of(14,30))
					.precioPromocional(3000.0)
					.tipoPromocion(TipoPromocion.HAPPYHOUR)
					.build();
			prom2.getImagenesPromocion().add(imgProm2);
			promocionRepository.save(prom2);

			suc1.getPromociones().add(prom1);
			suc1.getPromociones().add(prom2);
			sucursalRepository.save(suc1);

			UnidadMedida unMedida = UnidadMedida.builder()
					.denominacion("Unidad de medida 1")
					.build();
			unidadMedidaRepository.save(unMedida);

			ArticuloInsumo artInsumo1 = ArticuloInsumo.builder()
					.precioCompra(460.5)
					.stockActual(36)
					.stockMaximo(150)
					.esParaElaborar(Boolean.TRUE)
					.build();
			artInsumo1.getImagenesArticulos().add(img1);
			artInsumo1.setDenominacion("artInsumo1");
			artInsumo1.setPrecioVenta(200.0);
			artInsumo1.setUnidadMedida(unMedida);
			artInsumo1.setCategoria(cat1);
			articuloInsumoRepository.save(artInsumo1);

			ArticuloInsumo artInsumo2 = ArticuloInsumo.builder()
					.precioCompra(830.5)
					.stockActual(40)
					.stockMaximo(300)
					.esParaElaborar(Boolean.TRUE)
					.build();
			artInsumo2.getImagenesArticulos().add(img3);
			artInsumo2.setDenominacion("artInsumo2");
			artInsumo2.setPrecioVenta(500.0);
			artInsumo2.setUnidadMedida(unMedida);
			artInsumo2.setCategoria(cat1);
			articuloInsumoRepository.save(artInsumo2);

			ArticuloManufacturado artManuf1 = ArticuloManufacturado.builder()
					.descripcion("Descripcion art manuf 1")
					.tiempoEstimadoMinutos(60)
					.preparacion("Preparacion art manuf 1")
					.build();
			artManuf1.getImagenesArticulos().add(img4);
			artManuf1.setDenominacion("Articulo Manufacturado 1");
			artManuf1.setPrecioVenta(140.5);
			artManuf1.setUnidadMedida(unMedida);
			artManuf1.setCategoria(cat1);
			articuloManufacturadoRepository.save(artManuf1);

			ArticuloManufacturado artManuf2 = ArticuloManufacturado.builder()
					.descripcion("Descripcion art manuf 2")
					.tiempoEstimadoMinutos(40)
					.preparacion("Preparacion art manuf 2")
					.build();
			artManuf2.getImagenesArticulos().add(img2);
			artManuf2.setDenominacion("Articulo Manufacturado 2");
			artManuf2.setPrecioVenta(115.5);
			artManuf2.setUnidadMedida(unMedida);
			artManuf2.setCategoria(cat1);
			articuloManufacturadoRepository.save(artManuf2);

			ArticuloManufacturadoDetalle artManufDet1 = ArticuloManufacturadoDetalle.builder()
					.cantidad(2)
					.articuloInsumo(artInsumo1)
					.articuloManufacturado(artManuf1)
					.build();
			articuloManufacturadoDetalleRepository.save(artManufDet1);

			ArticuloManufacturadoDetalle artManufDet2 = ArticuloManufacturadoDetalle.builder()
					.cantidad(2)
					.articuloInsumo(artInsumo2)
					.articuloManufacturado(artManuf2)
					.build();
			articuloManufacturadoDetalleRepository.save(artManufDet2);

			Factura fac1= Factura.builder()
					.pedido(ped1)
					.fechaFacturacion(LocalDate.of(2024, 04, 12))
					.totalVenta(ped1.getTotal())
					.formaPago(FormaPago.EFECTIVO)
					.mpMerchantOrderId(1)
					.mpPaymentType("EFECTIVO")
					.mpPaymentId(1)
					.mpPreferenceId("3")
					.build();
			facturaRepository.save(fac1);

			DetallePedido detPed1 = DetallePedido.builder()
					.pedido(ped1)
					.articulo(artInsumo1)
					.articulo(artManuf1)
					.cantidad(5)
					.subTotal(450.5)
					.build();
			detallePedidoRepository.save(detPed1);

			DetallePedido detPed2 = DetallePedido.builder()
					.pedido(ped1)
					.articulo(artInsumo2)
					.articulo(artManuf2)
					.cantidad(2)
					.subTotal(300.0)
					.build();
			detallePedidoRepository.save(detPed2);

			DetallePromocion detPromo= DetallePromocion.builder()
					.cantidad(1)
					.promocion(prom1)
					.articulo(artManuf1)
					.build();
			promocionDetalleRepository.save(detPromo);

			DetallePromocion detPromo1= DetallePromocion.builder()
					.cantidad(2)
					.promocion(prom2)
					.articulo(artManuf2)
					.build();
			promocionDetalleRepository.save(detPromo1);
		};


	}

}

