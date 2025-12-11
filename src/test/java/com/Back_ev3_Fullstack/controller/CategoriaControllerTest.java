package com.Back_ev3_Fullstack.controller;

import com.Back_ev3_Fullstack.dto.CategoriaRequestDTO;
import com.Back_ev3_Fullstack.dto.CategoriaResponseDTO;
import com.Back_ev3_Fullstack.service.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoriaControllerTest {

    @InjectMocks
    private CategoriaController categoriaController;

    @Mock
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearCategoria() {
        CategoriaRequestDTO request = new CategoriaRequestDTO("Deportes");

        CategoriaResponseDTO responseDTO = new CategoriaResponseDTO(1L, "Deportes");

        when(categoriaService.crear(request)).thenReturn(responseDTO);

        CategoriaResponseDTO result = categoriaController.crear(request);

        assertEquals(responseDTO.getId(), result.getId());
        assertEquals(responseDTO.getNombre(), result.getNombre());

        verify(categoriaService, times(1)).crear(request);
    }

    @Test
    void testListarCategorias() {
        CategoriaResponseDTO cat1 = new CategoriaResponseDTO(1L, "Deportes");
        CategoriaResponseDTO cat2 = new CategoriaResponseDTO(2L, "Tecnología");

        when(categoriaService.listar()).thenReturn(List.of(cat1, cat2));

        List<CategoriaResponseDTO> result = categoriaController.listar();

        assertEquals(2, result.size());
        assertEquals("Deportes", result.get(0).getNombre());
        assertEquals("Tecnología", result.get(1).getNombre());

        verify(categoriaService, times(1)).listar();
    }
}