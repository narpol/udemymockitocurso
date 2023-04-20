package org.wfnar.appmockito.ejemplo.services;

import org.wfnar.appmockito.ejemplo.models.Examen;
import org.wfnar.appmockito.ejemplo.repositories.ExamenRepository;
import org.wfnar.appmockito.ejemplo.repositories.PreguntaRepository;

import java.util.List;
import java.util.Optional;

public class ExamenServiceImpl implements ExamenService{

    private ExamenRepository examenRepository;
    private PreguntaRepository preguntaRepository;

    public ExamenServiceImpl(ExamenRepository examenRepository, PreguntaRepository preguntaRepository) {
        this.examenRepository = examenRepository;
        this.preguntaRepository = preguntaRepository;
    }

    @Override
    public Optional<Examen> findExamenPorNombre(String nombre) {
         return examenRepository.findAll()
                 .stream()
                 .filter(e -> e.getNombre().contains(nombre))
                 .findFirst();

    }

    @Override
    public Examen findExamenPorNombreConPregunta(String nombre) {
        Optional<Examen> examenOptional = findExamenPorNombre(nombre);
        Examen examen = null;
        if(examenOptional.isPresent()){
            examen = examenOptional.orElseThrow();
            List<String> preguntas = preguntaRepository.findPreguntaPorExamenId(examen.getId());
            examen.setPreguntas(preguntas);
        }
        return examen;
    }
}
