package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.ExerciseGroupRequestDto;
import br.com.maisprati.api.dto.ExerciseGroupResponseDto;

import br.com.maisprati.api.mapper.ExerciseGroupMapper;
import br.com.maisprati.api.model.ExerciseGroup;
import br.com.maisprati.api.repository.ExerciseGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseGroupService {
    private final ExerciseGroupRepository exerciseGroupRepository;
    private final ExerciseGroupMapper mapper;

    public ExerciseGroupResponseDto criarGrupoExercicios(ExerciseGroupRequestDto exerciseGroupRequestDto) {
        ExerciseGroup exerciseGroup = mapper.toEntity(exerciseGroupRequestDto);
        ExerciseGroup exerciseResponse = exerciseGroupRepository.save(exerciseGroup);
        ExerciseGroupResponseDto exerciseGroupResponseDto = mapper.toResponse(exerciseGroup);
        return exerciseGroupResponseDto;
    }

}
