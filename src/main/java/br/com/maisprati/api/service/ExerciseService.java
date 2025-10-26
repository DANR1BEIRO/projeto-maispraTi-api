package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.ExercisePutRequestDto;
import br.com.maisprati.api.dto.ExerciseRequestDto;
import br.com.maisprati.api.dto.ExerciseResponseDto;
import br.com.maisprati.api.mapper.ExerciseMapper;
import br.com.maisprati.api.model.Exercise;
import br.com.maisprati.api.model.ExerciseGroup;
import br.com.maisprati.api.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper mapper;

    public ExerciseResponseDto criarExercicio(ExerciseRequestDto exerciseRequestDto){

        //Ver se já existe na base o exercício, se existe, mata o processo, se não segue para salvar
        if (exerciseRepository.findByPergunta(exerciseRequestDto.getPergunta()) != null) {
            throw new RuntimeException("Exercício já cadastrado!");
        }

        //Monta o objeto de entidade para salvar na base pela repository
        Exercise exercise = mapper.toEntity(exerciseRequestDto);

        //Executa save da repository para salvar exercício na base
        Exercise exerciseResponse = exerciseRepository.save(exercise);

        //Repassa os dados do execício salvo na base para um objeto de retorno para o usuário enxergar
        ExerciseResponseDto exerciseResponseDto = mapper.toResponse(exercise);

        //Retorna objeto de retorno
        return exerciseResponseDto;
    }

    public ExerciseResponseDto editarExercicio(Integer id, ExercisePutRequestDto exerciseRequestDto){
        Optional<Exercise> exerciseParaAtualizar = exerciseRepository.findById(id);

        //Ver se já existe na base o exercício, se existe, mata o processo, se não segue para salvar
        if (exerciseParaAtualizar.isEmpty()) {
            throw new RuntimeException("Exercício não existe!");
        }

        Exercise exercise = exerciseParaAtualizar.get();

        if (exerciseRequestDto.getTipo() != null){
            exercise.setTipo(exerciseRequestDto.getTipo());
        }

        if(exerciseRequestDto.getPergunta() != null){
            exercise.setPergunta(exerciseRequestDto.getPergunta());
        }

        if (exerciseRequestDto.getAlternativas() != null){
            exercise.setAlternativas(exerciseRequestDto.getAlternativas());
        }

        if (exerciseRequestDto.getRespostaCorreta() != null) {
            exercise.setRespostaCorreta(exerciseRequestDto.getRespostaCorreta());
        }

        if (exerciseRequestDto.getGrupoId() != null){
            ExerciseGroup group = new ExerciseGroup();
            group.setId(exerciseRequestDto.getGrupoId());
            exercise.setGrupo(group);
        }

        //Executa save da repository para salvar exercício na base
        Exercise exerciseResponse = exerciseRepository.save(exercise);

        //Repassa os dados do execício salvo na base para um objeto de retorno para o usuário enxergar
//        ExerciseResponseDto exerciseResponseDto = new ExerciseResponseDto();
//        exerciseResponseDto.setId(exerciseResponse.getId());
//        exerciseResponseDto.setGrupo(exerciseResponse.getGrupo());
//        exerciseResponseDto.setPergunta(exerciseResponse.getPergunta());
//        exerciseResponseDto.setAlternativas(exerciseResponse.getAlternativas());
//        exerciseResponseDto.setRespostaCorreta(exerciseResponse.getRespostaCorreta());
        ExerciseResponseDto exerciseResponseDto = mapper.toResponse(exercise);
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

//        ExerciseResponseDto exerciseResponseDto = new ExerciseResponseDto();
//        exerciseResponseDto.setId(exercise.getId());
//        exerciseResponseDto.setGrupo(exercise.getGrupo());
//        exerciseResponseDto.setPergunta(exercise.getPergunta());
//        exerciseResponseDto.setAlternativas(exercise.getAlternativas());
//        exerciseResponseDto.setRespostaCorreta(exercise.getRespostaCorreta());

        ExerciseResponseDto exerciseResponseDto = mapper.toResponse(exercise);

        return exerciseResponseDto;
    }

    public ExerciseResponseDto buscarExercicio(Integer id) {
        Optional <Exercise> exerciseParaBuscar = exerciseRepository.findById(id);

        if (exerciseParaBuscar.isEmpty()){
            System.out.println("Exercício não existe.");
        }

        //Exercise exercise = exerciseParaBuscar.get();
        Exercise exerciseResponse = exerciseRepository.getReferenceById(id);

//        ExerciseResponseDto exerciseResponseDto = new ExerciseResponseDto();
//        exerciseResponseDto.setId(exerciseResponse.getId());
//        exerciseResponseDto.setGrupo(exerciseResponse.getGrupo());
//        exerciseResponseDto.setPergunta(exerciseResponse.getPergunta());
//        exerciseResponseDto.setAlternativas(exerciseResponse.getAlternativas());
//        exerciseResponseDto.setRespostaCorreta(exerciseResponse.getRespostaCorreta());
        ExerciseResponseDto exerciseResponseDto = mapper.toResponse(exerciseResponse);
        return exerciseResponseDto;
    }
}