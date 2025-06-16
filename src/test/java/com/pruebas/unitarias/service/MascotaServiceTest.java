package com.pruebas.unitarias.service;

import com.pruebas.unitarias.model.Mascota;
import com.pruebas.unitarias.repository.MascotaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MascotaServiceTest {

    @Mock
    private MascotaRepository mascotaRepository;

    @InjectMocks
    private MascotaService mascotaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    /* Test para guardar mascota en la capa servicio */
    @Test
    void testGuardarMascota() {
        Mascota mascota = new Mascota(null, "Rex", "Perro", 5);
        Mascota mascotaGuardada = new Mascota(1L, "Rex", "Perro", 5);
        when(mascotaRepository.save(mascota)).thenReturn(mascotaGuardada);

        Mascota resultado = mascotaService.guardarMascota(mascota);
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Rex");
        assertThat(resultado.getTipo()).isEqualTo("Perro");
        assertThat(resultado.getEdad()).isEqualTo(5);
        verify(mascotaRepository).save(mascota);
    }

    @Test
    void testListarMascotas() {
        Mascota m1 = new Mascota(1L, "Rex", "Perro", 5);
        Mascota m2 = new Mascota(2L, "Michi", "Gato", 2);
        when(mascotaRepository.findAll()).thenReturn(Arrays.asList(m1, m2));

        List<Mascota> resultado = mascotaService.listarMascotas();
        assertThat(resultado).hasSize(2).contains(m1, m2);
        verify(mascotaRepository).findAll();
    }

    @Test 
    void testObtenerMascotaPorId(){
        Mascota m1 = new Mascota(1L, "Rex", "Perro", 5);
        //Mascota m2 = new Mascota(2L, "Michi", "Gato", 2);
        when(mascotaRepository.findById(m1.getId())).thenReturn(Optional.of(m1)); 

        Optional<Mascota> resultado = mascotaService.obtenerMascotaPorId(m1.getId());
        assertThat(resultado).contains(m1);
        verify(mascotaRepository).findById(1L);
    }

    @Test
    void testActualizarMascota(){
        Mascota m1 = new Mascota(1L, "Rex", "Perro", 5);
        Mascota m2 = new Mascota(1L, "Michi", "Gato", 2);
        when(mascotaRepository.findById(m1.getId())).thenReturn(Optional.of(m1));
        when(mascotaRepository.save(Mockito.any(Mascota.class))).thenReturn(m2);

        Mascota resultado = mascotaService.actualizarMascota(1L, m2);
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Michi");
        assertThat(resultado.getTipo()).isEqualTo("Gato");
        assertThat(resultado.getEdad()).isEqualTo(2);
        verify(mascotaRepository).findById(1L);


        
    }
    
}
