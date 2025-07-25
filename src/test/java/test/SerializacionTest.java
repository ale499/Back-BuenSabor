package test;

import com.example.MiPriApi.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

public class SerializacionTest {


    @Test
    public void serializarArticuloInsumo() throws Exception {
        Categoria categoria = Categoria.builder().denominacion("Insumos").build();
        UnidadMedida unidad = UnidadMedida.builder().denominacion("Kg").build();

        ArticuloInsumo insumo = ArticuloInsumo.builder()
                .denominacion("Harina")
                .categoria(categoria)
                .unidadMedida(unidad)
                .precioVenta(50.0)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(insumo);
        System.out.println(json);
    }

    @Test
    public void serializarArticuloManufacturado() throws Exception {
        Categoria categoria = Categoria.builder().denominacion("Manufacturados").build();
        UnidadMedida unidad = UnidadMedida.builder().denominacion("Unidad").build();

        ArticuloManufacturado manufacturado = ArticuloManufacturado.builder()
                .denominacion("Pizza")
                .categoria(categoria)
                .unidadMedida(unidad)
                .precioVenta(1200.0)
                .tiempoPreparacion(20)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(manufacturado);
        System.out.println(json);
    }

    @Test
    public void serializarArticuloManufacturadoDetalle() throws Exception {
        ArticuloManufacturado articulo = new ArticuloManufacturado();
        ArticuloInsumo insumo = new ArticuloInsumo();
        ArticuloManufacturadoDetalle detalle = ArticuloManufacturadoDetalle.builder()
                .cantidad(2)
                .articuloManufacturado(articulo)
                .articuloInsumo(insumo)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(detalle);
        System.out.println(json);
    }

    @Test
    public void serializarCategoria() throws Exception {
        Categoria categoria = Categoria.builder()
                .denominacion("Bebidas")
                .esInsumo(false)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(categoria);
        System.out.println(json);
    }

    @Test
    public void serializarCliente() throws Exception {
        Cliente cliente = Cliente.builder()
                .usuario(new Usuario())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(cliente);
        System.out.println(json);
    }

    @Test
    public void serializarDetallePedido() throws Exception {
        Pedido pedido = new Pedido();
        Articulo articulo = ArticuloInsumo.builder().denominacion("Harina").build();
        DetallePedido detalle = DetallePedido.builder()
                .cantidad(1)
                .subTotal(100.0)
                .pedido(pedido)
                .articulo(articulo)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(detalle);
        System.out.println(json);
    }

    @Test
    public void serializarDetallePromocion() throws Exception {
        Promocion promo = new Promocion();
        Articulo articulo = ArticuloManufacturado.builder().denominacion("Pizza").build();
        DetallePromocion detalle = DetallePromocion.builder()
                .cantidad(3)
                .promocion(promo)
                .articulo(articulo)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(detalle);
        System.out.println(json);
    }

    @Test
    public void serializarDomicilio() throws Exception {
        Localidad localidad = new Localidad();
        Domicilio domicilio = Domicilio.builder()
                .calle("Calle Falsa")
                .numero(123)
                .localidad(localidad)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(domicilio);
        System.out.println(json);
    }

    @Test
    public void serializarLocalidad() throws Exception {
        Provincia provincia = new Provincia();
        Localidad localidad = Localidad.builder()
                .nombre("Centro")
                .provincia(provincia)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(localidad);
        System.out.println(json);
    }

    @Test
    public void serializarPedido() throws Exception {
        DetallePedido detalle = new DetallePedido();
        Pedido pedido = Pedido.builder()
                .numeroPedido(1)
                .horaEstimadaFinalizacion(LocalTime.now())
                .fechaPedido(LocalDate.now())
                .detalles(Collections.singletonList(detalle))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Para manejar LocalDate y LocalTime
        String json = mapper.writeValueAsString(pedido);
        System.out.println(json);
    }

    @Test
    public void serializarPromocion() throws Exception {
        Promocion promo = Promocion.builder()
                .denominacion("Promo 2x1")
                .fechaDesde(LocalDate.now())
                .fechaHasta(LocalDate.now().plusDays(10))
                .horaDesde(LocalTime.of(10, 0))
                .horaHasta(LocalTime.of(22, 0))
                .descripcionDescuento("Dos por uno en bebidas")
                .precioPromocional(100.0)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Para manejar LocalDate y LocalTime
        String json = mapper.writeValueAsString(promo);
        System.out.println(json);
    }

    @Test
    public void serializarProvincia() throws Exception {
        Pais pais = new Pais();
        Provincia provincia = Provincia.builder()
                .nombre("Buenos Aires")
                .pais(pais)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(provincia);
        System.out.println(json);
    }

    @Test
    public void serializarSucursal() throws Exception {
        Domicilio domicilio = new Domicilio();
        Sucursal sucursal = Sucursal.builder()
                .nombre("Sucursal Centro")
                .domicilio(domicilio)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(sucursal);
        System.out.println(json);
    }

    @Test
    public void serializarRolesYUser() throws Exception {
        Roles rol = Roles.builder()
                .name("ADMIN")
                .description("Administrador")
                .build();

        User user = User.builder()
                .userEmail("test@mail.com")
                .name("Juan")
                .lastName("Pérez")
                .nickName("jperez")
                .roles(Collections.singleton(rol))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        System.out.println(json);
    }




}

