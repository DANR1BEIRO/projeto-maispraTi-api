package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.ExerciseGroupRequestDto;
import br.com.maisprati.api.dto.ExerciseGroupResponseDto;

import br.com.maisprati.api.mapper.ExerciseGroupMapper;
import br.com.maisprati.api.model.ExerciseGroup;
import br.com.maisprati.api.repository.ExerciseGroupRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public List<ExerciseGroupResponseDto> buscarTodosGruposExercicios(){
        List<ExerciseGroup> exerciseGroupList = exerciseGroupRepository.findAll();
        List<ExerciseGroupResponseDto> exerciseGroupResponseDtoList = mapper.toResponseList(exerciseGroupList);
        return exerciseGroupResponseDtoList;
    }

    /**
     * Busca a entidade ExerciseGroup pelo ID e valida sua existência.
     * Usa orElseThrow() para tratamento limpo de recurso não encontrado.
     * @param id O ID do grupo.
     * @return A entidade ExerciseGroup (nunca null).
     */

    @Transactional(readOnly = true)
    public ExerciseGroup buscarGrupoPorId(Integer id) {

        // Usa findById e lança a exceção se não encontrar.
        return exerciseGroupRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Grupo de exercício não encontrado com ID: " + id));
    }
}
