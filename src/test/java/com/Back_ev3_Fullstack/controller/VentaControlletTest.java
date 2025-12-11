package com.Back_ev3_Fullstack.controller;

import com.Back_ev3_Fullstack.dto.VentaRequest;
import com.Back_ev3_Fullstack.dto.VentaResponse;
import com.Back_ev3_Fullstack.service.VentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VentaControllerTest {

    @InjectMocks
    private VentaController ventaController;

    @Mock
    private VentaService ventaService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearVenta() {
        when(authentication.getName()).thenReturn("user@example.com");

        VentaRequest.ItemVenta item = new VentaRequest.ItemVenta(1L, 2);
        VentaRequest request = new VentaRequest(List.of(item), 200);

        VentaResponse.ProductoInfo productoInfo = new VentaResponse.ProductoInfo(1L, "Producto1", 1L, "Categoria1");
        VentaResponse.DetalleResponse detalle = new VentaResponse.DetalleResponse(1L, productoInfo, 2, 100, 200);
        VentaResponse response = new VentaResponse(1L, LocalDateTime.now(), 200, List.of(detalle));

        when(ventaService.crearVenta(request, "user@example.com")).thenReturn(response);

        ResponseEntity<VentaResponse> result = ventaController.crearVenta(request, authentication);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(1L, result.getBody().getId());
        assertEquals(200, result.getBody().getTotal());
        verify(ventaService, times(1)).crearVenta(request, "user@example.com");
    }

    @Test
    void testVentasUsuario() {
        when(authentication.getName()).thenReturn("user@example.com");

        VentaResponse.ProductoInfo productoInfo = new VentaResponse.ProductoInfo(1L, "Producto1", 1L, "Categoria1");
        VentaResponse.DetalleResponse detalle = new VentaResponse.DetalleResponse(1L, productoInfo, 2, 100, 200);
        VentaResponse response = new VentaResponse(1L, LocalDateTime.now(), 200, List.of(detalle));

        when(ventaService.listarVentasDeUsuario("user@example.com")).thenReturn(List.of(response));

        ResponseEntity<List<VentaResponse>> result = ventaController.ventasUsuario(authentication);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, result.getBody().size());
        assertEquals(200, result.getBody().get(0).getTotal());
        verify(ventaService, times(1)).listarVentasDeUsuario("user@example.com");
    }

    @Test
    void testVentasAdmin() {
        VentaResponse.ProductoInfo productoInfo = new VentaResponse.ProductoInfo(1L, "Producto1", 1L, "Categoria1");
        VentaResponse.DetalleResponse detalle = new VentaResponse.DetalleResponse(1L, productoInfo, 2, 100, 200);
        VentaResponse response = new VentaResponse(1L, LocalDateTime.now(), 200, List.of(detalle));

        when(ventaService.listarVentas()).thenReturn(List.of(response));

        ResponseEntity<List<VentaResponse>> result = ventaController.ventasAdmin();

        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, result.getBody().size());
        assertEquals(200, result.getBody().get(0).getTotal());
        verify(ventaService, times(1)).listarVentas();
    }
}