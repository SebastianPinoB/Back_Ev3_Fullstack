package com.Back_ev3_Fullstack.repository;

import com.Back_ev3_Fullstack.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UsuarioRepositoryTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .correo("test@example.com")
                .contrasenia("1234")
                .build();
    }

    @Test
    void findByCorreo_devuelveUsuario() {
        when(usuarioRepository.findByCorreo("test@example.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioRepository.findByCorreo("test@example.com");

        assertTrue(resultado.isPresent());
        assertEquals("test@example.com", resultado.get().getCorreo());
        verify(usuarioRepository, times(1)).findByCorreo("test@example.com");
    }

    @Test
    void findByCorreo_noExiste() {
        when(usuarioRepository.findByCorreo("no@existe.com")).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioRepository.findByCorreo("no@existe.com");

        assertFalse(resultado.isPresent());
        verify(usuarioRepository, times(1)).findByCorreo("no@existe.com");
    }

    @Test
    void existsByCorreo_devuelveTrue() {
        when(usuarioRepository.existsByCorreo("test@example.com")).thenReturn(true);

        boolean resultado = usuarioRepository.existsByCorreo("test@example.com");

        assertTrue(resultado);
        verify(usuarioRepository, times(1)).existsByCorreo("test@example.com");
    }

    @Test
    void existsByCorreo_devuelveFalse() {
        when(usuarioRepository.existsByCorreo("no@existe.com")).thenReturn(false);

        boolean resultado = usuarioRepository.existsByCorreo("no@existe.com");

        assertFalse(resultado);
        verify(usuarioRepository, times(1)).existsByCorreo("no@existe.com");
    }
}