package org.wfnar.appmockito.ejemplo.services;

import org.wfnar.appmockito.ejemplo.models.Examen;

import java.util.Optional;

public interface ExamenService {
    Optional<Examen> findExamenPorNombre(String nombre);
    Examen findExamenPorNombreConPregunta(String nombre);
}
