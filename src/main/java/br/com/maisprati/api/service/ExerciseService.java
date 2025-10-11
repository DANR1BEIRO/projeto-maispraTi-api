package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.ExerciseDto;
import br.com.maisprati.api.dto.ExerciseResponseDto;
import br.com.maisprati.api.model.Exercise;
import br.com.maisprati.api.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseResponseDto criarExercicio(ExerciseDto exerciseDto){

        //Ver se já existe na base o exercício, se existe, mata o processo, se não segue para salvar
        if (exerciseRepository.findByPergunta(exerciseDto.getPergunta()) != null) {
            throw new RuntimeException("Exercício já cadastrado!");
        }

        //Monta o objeto de entidade para salvar na base pela repository
        Exercise exercise = new Exercise();
        exercise.setGrupo(exerciseDto.getGrupo());
        exercise.setPergunta(exerciseDto.getPergunta());
        exercise.setAlternativas(exerciseDto.getAlternativas());
        exercise.setRespostaCorreta(exerciseDto.getRespostaCorreta());

        //Executa save da repository para salvar exercício na base
        Exercise exerciseResponse = exerciseRepository.save(exercise);

        //Repassa os dados do execício salvo na base para um objeto de retorno para o usuário enxergar
        ExerciseResponseDto exerciseResponseDto = new ExerciseResponseDto();
        exerciseResponseDto.setId(exerciseResponse.getId());
        exerciseResponseDto.setGrupo(exerciseResponse.getGrupo());
        exerciseResponseDto.setPergunta(exerciseResponse.getPergunta());
        exerciseResponseDto.setAlternativas(exerciseResponse.getAlternativas());
        exerciseResponseDto.setRespostaCorreta(exerciseResponse.getRespostaCorreta());

        //Retorna objeto de retorno
        return exerciseResponseDto;
    }

    public ExerciseResponseDto editarExercicio(Integer id, ExerciseDto exerciseDto){
        Optional<Exercise> exerciseParaAtualizar = exerciseRepository.findById(id);

        //Ver se já existe na base o exercício, se existe, mata o processo, se não segue para salvar
        if (exerciseParaAtualizar.isEmpty()) {
            throw new RuntimeException("Exercício não existe!");
        }

        Exercise exercise = exerciseParaAtualizar.get();

        if (exerciseDto.getGrupo() != null){
            exercise.setGrupo(exerciseDto.getGrupo());
        }

        if(exerciseDto.getPergunta() != null){
            exercise.setPergunta(exerciseDto.getPergunta());
        }

        if (exerciseDto.getAlternativas() != null){
            exercise.setAlternativas(exerciseDto.getAlternativas());
        }

        if (exerciseDto.getRespostaCorreta() != null) {
            exercise.setRespostaCorreta(exerciseDto.getRespostaCorreta());
        }

        //Executa save da repository para salvar exercício na base
        Exercise exerciseResponse = exerciseRepository.save(exercise);

        //Repassa os dados do execício salvo na base para um objeto de retorno para o usuário enxergar
        ExerciseResponseDto exerciseResponseDto = new ExerciseResponseDto();
        exerciseResponseDto.setId(exerciseResponse.getId());
        exerciseResponseDto.setGrupo(exerciseResponse.getGrupo());
        exerciseResponseDto.setPergunta(exerciseResponse.getPergunta());
        exerciseResponseDto.setAlternativas(exerciseResponse.getAlternativas());
        exerciseResponseDto.setRespostaCorreta(exerciseResponse.getRespostaCorreta());

        //Retorna objeto de retorno
        return exerciseResponseDto;
    }

    public ExerciseResponseDto excluirExercicio(Integer id){
        Optional <Exercise> exerciseParaExcluir = exerciseRepository.findById(id);

        if(exerciseParaExcluir.isEmpty()){
            System.out.println("Não foi possível excluir pois exercício não existe!");
        }

        Exercise exercise = exerciseParaExcluir.get();

        exerciseRepository.deleteById(id);

        ExerciseResponseDto exerciseResponseDto = new ExerciseResponseDto();
        exerciseResponseDto.setId(exercise.getId());
        exerciseResponseDto.setGrupo(exercise.getGrupo());
        exerciseResponseDto.setPergunta(exercise.getPergunta());
        exerciseResponseDto.setAlternativas(exercise.getAlternativas());
        exerciseResponseDto.setRespostaCorreta(exercise.getRespostaCorreta());

        return exerciseResponseDto;
    }

    public ExerciseResponseDto buscarExercicio(Integer id) {
        Optional <Exercise> exerciseParaBuscar = exerciseRepository.findById(id);

        if (exerciseParaBuscar.isEmpty()){
            System.out.println("Exercício não existe.");
        }

        //Exercise exercise = exerciseParaBuscar.get();
        Exercise exerciseResponse = exerciseRepository.getReferenceById(id);

        ExerciseResponseDto exerciseResponseDto = new ExerciseResponseDto();
        exerciseResponseDto.setId(exerciseResponse.getId());
        exerciseResponseDto.setGrupo(exerciseResponse.getGrupo());
        exerciseResponseDto.setPergunta(exerciseResponse.getPergunta());
        exerciseResponseDto.setAlternativas(exerciseResponse.getAlternativas());
        exerciseResponseDto.setRespostaCorreta(exerciseResponse.getRespostaCorreta());

        return exerciseResponseDto;
    }
}