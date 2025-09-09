package com.example.MiPriApi.controllers;
import com.example.MiPriApi.entities.enums.Estado;
import com.example.MiPriApi.repositories.DetallePedidoRepository;
import com.example.MiPriApi.repositories.PedidoRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.ByteArrayOutputStream;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/grafico")
public class GraficosController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @GetMapping("/ventas/total")
    public Double getTotalVentas() {
        return pedidoRepository.sumTotalByEstado(Estado.ENTREGADO);
    }

    @GetMapping("/pedidos/total")
    public Long getTotalPedidos() {
        return pedidoRepository.count();
    }

    @GetMapping("/productos/mas-vendidos")
    public List<Map<String, Object>> getProductosMasVendidos() {
        List<Object[]> results = detallePedidoRepository.findProductosMasVendidos();
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("producto", row[0]);
            item.put("cantidad", row[1]);
            response.add(item);
        }
        return response;
    }

    @GetMapping("/productos/total-vendidos")
    public Long getTotalProductosVendidos() {
        return detallePedidoRepository.findTotalProductosVendidos();
    }

    //EXCEL

    @GetMapping("/productos/ventas-excel")
    public ResponseEntity<byte[]> exportarVentasManufacturadosExcel(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) throws Exception {

        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);

        // Query ventas for the period (you need a repository method for this)
        List<Object[]> ventas = detallePedidoRepository.findVentasPorManufacturadoEnPeriodo(inicio, fin);
        double sumaTotal = ventas.stream().mapToDouble(row -> ((Number) row[2]).doubleValue()).sum();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ventas Manufacturados");
        int rowIdx = 0;

        Row header = sheet.createRow(rowIdx++);
        header.createCell(0).setCellValue("Artículo Manufacturado");
        header.createCell(1).setCellValue("Cantidad Vendida");
        header.createCell(2).setCellValue("Total Ventas");

        for (Object[] row : ventas) {
            Row dataRow = sheet.createRow(rowIdx++);
            dataRow.createCell(0).setCellValue(row[0].toString());
            dataRow.createCell(1).setCellValue(((Number) row[1]).intValue());
            dataRow.createCell(2).setCellValue(((Number) row[2]).doubleValue());
        }

        Row totalRow = sheet.createRow(rowIdx);
        totalRow.createCell(1).setCellValue("TOTAL");
        totalRow.createCell(2).setCellValue(sumaTotal);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ventas_manufacturados.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(out.toByteArray());
    }

    @GetMapping("/productos/mas-vendidos-excel")
    public ResponseEntity<byte[]> exportarRankingComidasExcel(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) throws Exception {

        // Parse dates
        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);

        // Query ranking for the period
        List<Object[]> ranking = detallePedidoRepository.findProductosMasVendidosEnPeriodo(inicio, fin);

        // Create Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ranking Comidas");
        int rowIdx = 0;

        // Header
        Row header = sheet.createRow(rowIdx++);
        header.createCell(0).setCellValue("Producto");
        header.createCell(1).setCellValue("Cantidad Pedida");

        // Data rows
        for (Object[] row : ranking) {
            Row dataRow = sheet.createRow(rowIdx++);
            dataRow.createCell(0).setCellValue(row[0].toString());
            dataRow.createCell(1).setCellValue(((Number) row[1]).intValue());
        }

        // Write to byte array
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        // Return as response
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ranking_comidas.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(out.toByteArray());
    }

    @GetMapping("/pedidos/por-cliente-auth0")
    public List<Map<String, Object>> getPedidosPorClienteAuth0EmailEnPeriodo(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) {

        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);

        List<Object[]> results = pedidoRepository.countPedidosPorClienteAuth0EmailEnPeriodo(inicio, fin);
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> item = new HashMap<>();
            item.put("clienteEmail", row[0]);
            item.put("cantidadPedidos", row[1]);
            response.add(item);
        }
        return response;
    }

    @GetMapping("/pedidos/por-cliente-auth0-excel")
    public ResponseEntity<byte[]> exportPedidosPorClienteAuth0EmailExcel(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) throws Exception {

        LocalDate inicio = LocalDate.parse(fechaInicio);
        LocalDate fin = LocalDate.parse(fechaFin);

        List<Object[]> results = pedidoRepository.countPedidosPorClienteAuth0EmailEnPeriodo(inicio, fin);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Pedidos por Cliente Email");
        int rowIdx = 0;

        Row header = sheet.createRow(rowIdx++);
        header.createCell(0).setCellValue("Cliente Email");
        header.createCell(1).setCellValue("Cantidad de Pedidos");

        for (Object[] row : results) {
            Row dataRow = sheet.createRow(rowIdx++);
            dataRow.createCell(0).setCellValue(row[0] != null ? row[0].toString() : "");
            dataRow.createCell(1).setCellValue(((Number) row[1]).intValue());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pedidos_por_cliente_email.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(out.toByteArray());
    }


}
