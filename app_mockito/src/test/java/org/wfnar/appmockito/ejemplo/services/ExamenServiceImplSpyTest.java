package org.wfnar.appmockito.ejemplo.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.wfnar.appmockito.ejemplo.Datos;
import org.wfnar.appmockito.ejemplo.models.Examen;
import org.wfnar.appmockito.ejemplo.repositories.ExamenRepository;
import org.wfnar.appmockito.ejemplo.repositories.ExamenRepositoryImpl;
import org.wfnar.appmockito.ejemplo.repositories.PreguntaRepository;
import org.wfnar.appmockito.ejemplo.repositories.PreguntaRepositoryImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplSpyTest {
    @Spy
    ExamenRepositoryImpl repository;
    @Spy
    PreguntaRepositoryImpl preguntaRepository;
    @InjectMocks
    ExamenServiceImpl service;


    @Test
    void testSpy() {

        List<String> preguntas = Arrays.asList("aritmética");
//        when(preguntaRepository.findPreguntaPorExamenId(anyLong())).thenReturn(preguntas);
        doReturn(preguntas).when(preguntaRepository).findPreguntaPorExamenId(anyLong());

        Examen examen = service.findExamenPorNombreConPregunta("Matemáticas");
        assertEquals(5, examen.getId());
        assertEquals("Matemáticas", examen.getNombre());
        assertEquals(1, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("aritmética"));

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntaPorExamenId(anyLong());
    }

    @Test
    void testOrdenDeInvocaciones() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);

        service.findExamenPorNombreConPregunta("Matemáticas");
        service.findExamenPorNombreConPregunta("Lenguaje");

        InOrder inOrder = inOrder(preguntaRepository);
        inOrder.verify(preguntaRepository).findPreguntaPorExamenId(5L);
        inOrder.verify(preguntaRepository).findPreguntaPorExamenId(6L);

    }

   /* @Test
    void testOrdenDeInvocaciones2() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);

        service.findExamenPorNombreConPreguntas("Matemáticas");
        service.findExamenPorNombreConPreguntas("Lenguaje");

        InOrder inOrder = inOrder(repository, preguntaRepository);
        inOrder.verify(repository).findAll();
        inOrder.verify(preguntaRepository).findPreguntasPorExamenId(5L);

        inOrder.verify(repository).findAll();
        inOrder.verify(preguntaRepository).findPreguntasPorExamenId(6L);

    }

    @Test
    void testNumeroDeInvocaciones() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        service.findExamenPorNombreConPreguntas("Matemáticas");

        verify(preguntaRepository).findPreguntasPorExamenId(5L);
        verify(preguntaRepository, times(1)).findPreguntasPorExamenId(5L);
        verify(preguntaRepository, atLeast(1)).findPreguntasPorExamenId(5L);
        verify(preguntaRepository, atLeastOnce()).findPreguntasPorExamenId(5L);
        verify(preguntaRepository, atMost(1)).findPreguntasPorExamenId(5L);
        verify(preguntaRepository, atMostOnce()).findPreguntasPorExamenId(5L);
    }

    @Test
    void testNumeroDeInvocaciones2() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        service.findExamenPorNombreConPreguntas("Matemáticas");

//        verify(preguntaRepository).findPreguntasPorExamenId(5L); falla
        verify(preguntaRepository, times(2)).findPreguntasPorExamenId(5L);
        verify(preguntaRepository, atLeast(2)).findPreguntasPorExamenId(5L);
        verify(preguntaRepository, atLeastOnce()).findPreguntasPorExamenId(5L);
        verify(preguntaRepository, atMost(20)).findPreguntasPorExamenId(5L);
//        verify(preguntaRepository, atMostOnce()).findPreguntasPorExamenId(5L); falla
    }

    @Test
    void testNumeroInvocaciones3() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        service.findExamenPorNombreConPreguntas("Matemáticas");

        verify(preguntaRepository, never()).findPreguntasPorExamenId(5L);
        verifyNoInteractions(preguntaRepository);

        verify(repository).findAll();
        verify(repository, times(1)).findAll();
        verify(repository, atLeast(1)).findAll();
        verify(repository, atLeastOnce()).findAll();
        verify(repository, atMost(10)).findAll();
        verify(repository, atMostOnce()).findAll();
    }*/
}