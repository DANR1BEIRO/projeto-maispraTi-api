package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.ExercisePutRequestDto;
import br.com.maisprati.api.dto.ExerciseRequestDto;
import br.com.maisprati.api.dto.ExerciseResponseDto;
import br.com.maisprati.api.mapper.ExerciseMapper;
import br.com.maisprati.api.model.Exercise;
import br.com.maisprati.api.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper mapper;

    public ExerciseResponseDto criarExercicio(ExerciseRequestDto exerciseRequestDto) {
        if (exerciseRepository.findByPergunta(exerciseRequestDto.getPergunta()) != null) {
            throw new RuntimeException("Exercício já cadastrado!");
        }

        Exercise exercise = mapper.toEntity(exerciseRequestDto);
        exerciseRepository.save(exercise);
        return mapper.toResponse(exercise);
    }

    public ExerciseResponseDto editarExercicio(Integer id, ExercisePutRequestDto exerciseRequestDto) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercício não existe!"));

        if (exerciseRequestDto.getTipo() != null) {
            exercise.setTipo(exerciseRequestDto.getTipo());
        }
        if (exerciseRequestDto.getPergunta() != null) {
            exercise.setPergunta(exerciseRequestDto.getPergunta());
        }
        if (exerciseRequestDto.getAlternativas() != null) {
            exercise.setAlternativas(exerciseRequestDto.getAlternativas());
        }
        if (exerciseRequestDto.getRespostaCorreta() != null) {
            exercise.setRespostaCorreta(exerciseRequestDto.getRespostaCorreta());
        }

        exerciseRepository.save(exercise);
        return mapper.toResponse(exercise);
    }

    public ExerciseResponseDto excluirExercicio(Integer id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercício não existe!"));

        exerciseRepository.delete(exercise);
        return mapper.toResponse(exercise);
    }

    public ExerciseResponseDto buscarExercicio(Integer id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercício não existe."));
        return mapper.toResponse(exercise);
    }
}
