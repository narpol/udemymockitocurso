package org.wfnar.appmockito.ejemplo.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.wfnar.appmockito.ejemplo.models.Examen;
import org.wfnar.appmockito.ejemplo.repositories.ExamenRepository;
import org.wfnar.appmockito.ejemplo.repositories.ExamenRepositoryOtro;
import org.wfnar.appmockito.ejemplo.repositories.PreguntaRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest {
    @Mock
    ExamenRepository repository;
    @Mock
    PreguntaRepository preguntaRepository;
    @InjectMocks
    ExamenServiceImpl service;

    /*@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = mock(ExamenRepositoryOtro.class);
        preguntaRepository = mock(PreguntaRepository.class);
        service = new ExamenServiceImpl(repository, preguntaRepository);
    }*/

    @Test
    void findExamenPorNombre() {


        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        Optional<Examen> examen = service.findExamenPorNombre("Matemáticas");

        assertTrue(examen.isPresent());
        assertEquals(5L, examen.orElseThrow().getId());
        assertEquals("Matemáticas", examen.get().getNombre());

    }

    @Test
    void findExamenPorNombreListaVacia() {

        List<Examen> datos = Collections.emptyList() ;

        when(repository.findAll()).thenReturn(datos  );
        Optional<Examen> examen = service.findExamenPorNombre("Matemáticas");

        assertFalse(examen.isPresent());

    }

    @Test
    void testPreguntsExamen(){
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntaPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPregunta("Matemáticas");
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("aritmética"));
    }

    @Test
    void testPreguntsExamenVerify(){
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntaPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPregunta("Matemáticas");
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("aritmética"));
        verify(repository).findAll();
        verify(preguntaRepository).findPreguntaPorExamenId(anyLong());
    }

    @Test
    void testNoExisteExamenVerify(){
        //Given
        when(repository.findAll()).thenReturn(Collections.emptyList());
        when(preguntaRepository.findPreguntaPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        //When
        Examen examen = service.findExamenPorNombreConPregunta("Matemáticas");
        //Then
        assertNull(examen);
        verify(repository).findAll();
        verify(preguntaRepository).findPreguntaPorExamenId(5L);
    }

    @Test
    void testGuardarExamen() {

        //Given
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);

        when(repository.guardar(any(Examen.class))).then(new Answer<Examen>(){
            Long secuencia = 8L;

            @Override
            public Examen answer(InvocationOnMock invocation) throws Throwable {
                Examen examen = invocation.getArgument(0);
                examen.setId(secuencia++);
                return examen;
            }
        });
        //When
        Examen examen = service.guardar(newExamen);

        //Then
        assertNotNull(examen.getId());
        assertEquals(8L, examen.getId());
        assertEquals("Fisica", examen.getNombre());

        verify(repository).guardar(any(Examen.class));
        verify(preguntaRepository).guardarVarias(anyList());
    }
}