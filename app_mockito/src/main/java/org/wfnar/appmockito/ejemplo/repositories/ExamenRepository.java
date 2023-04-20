package org.wfnar.appmockito.ejemplo.repositories;

import org.wfnar.appmockito.ejemplo.models.Examen;

import java.util.List;

public interface ExamenRepository {

    List<Examen> findAll();

    Examen guardar(Examen examen);
}
