package org.wfnar.appmockito.ejemplo.repositories;

import org.wfnar.appmockito.ejemplo.Datos;

import java.util.List;

public class PreguntaRepositoryImpl implements PreguntaRepository{
    @Override
    public List<String> findPreguntaPorExamenId(Long id) {
        System.out.println("PreguntaRepositoryImpl.findPreguntaPorExamenId");
        return Datos.PREGUNTAS;
    }

    @Override
    public void guardarVarias(List<String> preguntas) {
        System.out.println("PreguntaRepositoryImpl.guardarVarias");
    }
}
