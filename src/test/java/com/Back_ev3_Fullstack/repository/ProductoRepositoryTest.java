package com.Back_ev3_Fullstack.repository;

import com.Back_ev3_Fullstack.entity.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoRepositoryTest {

    @Mock
    private ProductoRepository productoRepository;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = Producto.builder()
                .id(1L)
                .nombre("Producto Test")
                .precio(1000)
                .stock(10)
                .build();
    }

    @Test
    void existsByNombre_devuelveTrueSiExiste() {
        when(productoRepository.existsByNombre("Producto Test")).thenReturn(true);

        boolean resultado = productoRepository.existsByNombre("Producto Test");

        assertTrue(resultado);
        verify(productoRepository, times(1)).existsByNombre("Producto Test");
    }

    @Test
    void existsByNombre_devuelveFalseSiNoExiste() {
        when(productoRepository.existsByNombre("No Existe")).thenReturn(false);

        boolean resultado = productoRepository.existsByNombre("No Existe");

        assertFalse(resultado);
        verify(productoRepository, times(1)).existsByNombre("No Existe");
    }

    @Test
    void findById_devuelveProducto() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> resultado = productoRepository.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Producto Test", resultado.get().getNombre());
    }
}