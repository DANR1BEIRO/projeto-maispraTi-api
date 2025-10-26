package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.ExerciseGroupResponseDto;
import br.com.maisprati.api.dto.ExerciseListRequestDto;
import br.com.maisprati.api.dto.ExerciseListResponseDto;
import br.com.maisprati.api.dto.ExerciseResponseDto;
import br.com.maisprati.api.mapper.ExerciseListMapper;
import br.com.maisprati.api.model.ExerciseList;
import br.com.maisprati.api.repository.ExerciseListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseListService {
    private final ExerciseListRepository exerciseListRepository;
    private final ExerciseListMapper mapper;

    public ExerciseListResponseDto criarListaExercicios(ExerciseListRequestDto exerciseListRequestDto) {
        ExerciseList exerciseList = mapper.toEntity(exerciseListRequestDto);
        ExerciseList exerciseResponse = exerciseListRepository.save(exerciseList);
        ExerciseListResponseDto exerciseListResponseDto = mapper.toResponse(exerciseList);
        return exerciseListResponseDto;
    }

    public List<ExerciseListResponseDto> buscarTodasListasExercicios(){
        List<ExerciseList> exerciseList = exerciseListRepository.findAll();
        List<ExerciseListResponseDto> exerciseListResponseDto = mapper.toResponseList(exerciseList);
        return exerciseListResponseDto;
    }


}
