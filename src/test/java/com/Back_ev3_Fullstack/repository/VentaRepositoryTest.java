package com.Back_ev3_Fullstack.repository;

import com.Back_ev3_Fullstack.entity.Usuario;
import com.Back_ev3_Fullstack.entity.Venta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class VentaRepositoryTest {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void findAllByUsuarioCorreo_devuelveVentasDelUsuario() {
        // Crear y guardar un usuario
        Usuario usuario = Usuario.builder()
                .correo("usuario@test.com")
                .contrasenia("1234")
                .build();
        usuario = usuarioRepository.save(usuario);

        // Crear y guardar ventas asociadas al usuario
        Venta venta1 = Venta.builder()
                .usuario(usuario)
                .total(1000)
                .build();
        Venta venta2 = Venta.builder()
                .usuario(usuario)
                .total(2000)
                .build();
        ventaRepository.save(venta1);
        ventaRepository.save(venta2);

        // Ejecutar método a probar
        List<Venta> ventas = ventaRepository.findAllByUsuarioCorreo("usuario@test.com");

        // Verificar resultados
        assertEquals(2, ventas.size());
        assertTrue(ventas.stream().allMatch(v -> v.getUsuario().getCorreo().equals("usuario@test.com")));
    }

    @Test
    void findAllByUsuarioCorreo_noExisteUsuario() {
        List<Venta> ventas = ventaRepository.findAllByUsuarioCorreo("no@existe.com");
        assertTrue(ventas.isEmpty());
    }
}
