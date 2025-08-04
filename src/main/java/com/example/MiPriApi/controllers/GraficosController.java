package com.example.MiPriApi.controllers;
import com.example.MiPriApi.entities.enums.Estado;
import com.example.MiPriApi.repositories.DetallePedidoRepository;
import com.example.MiPriApi.repositories.PedidoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.ByteArrayOutputStream;


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

    @GetMapping("/productos/ventas-excel")
    public ResponseEntity<byte[]> exportarVentasManufacturadosExcel() throws Exception {
        // 1. Get data (replace with your actual query)
        List<Object[]> ventas = detallePedidoRepository.findVentasPorManufacturado(); // [nombre, cantidad, total]
        double sumaTotal = ventas.stream().mapToDouble(row -> ((Number) row[2]).doubleValue()).sum();

        // 2. Create Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ventas Manufacturados");
        int rowIdx = 0;

        // Header
        Row header = sheet.createRow(rowIdx++);
        header.createCell(0).setCellValue("Artículo Manufacturado");
        header.createCell(1).setCellValue("Cantidad Vendida");
        header.createCell(2).setCellValue("Total Ventas");

        // Data rows
        for (Object[] row : ventas) {
            Row dataRow = sheet.createRow(rowIdx++);
            dataRow.createCell(0).setCellValue(row[0].toString());
            dataRow.createCell(1).setCellValue(((Number) row[1]).intValue());
            dataRow.createCell(2).setCellValue(((Number) row[2]).doubleValue());
        }

        // Total row
        Row totalRow = sheet.createRow(rowIdx);
        totalRow.createCell(1).setCellValue("TOTAL");
        totalRow.createCell(2).setCellValue(sumaTotal);

        // Write to byte array
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        // 3. Return as response
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ventas_manufacturados.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(out.toByteArray());
    }


}
