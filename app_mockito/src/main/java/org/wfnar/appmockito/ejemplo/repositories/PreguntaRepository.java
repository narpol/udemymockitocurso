package org.wfnar.appmockito.ejemplo.repositories;

import java.util.List;

public interface PreguntaRepository {

    List<String> findPreguntaPorExamenId(Long id);
}
