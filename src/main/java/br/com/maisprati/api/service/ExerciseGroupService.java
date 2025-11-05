package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.ExerciseGroupRequestDto;
import br.com.maisprati.api.dto.ExerciseGroupResponseDto;

import br.com.maisprati.api.mapper.ExerciseGroupMapper;
import br.com.maisprati.api.model.ExerciseGroup;
import br.com.maisprati.api.model.ExerciseList;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.model.UserExerciseResult;
import br.com.maisprati.api.repository.ExerciseGroupRepository;
import br.com.maisprati.api.repository.ExerciseListRepository;
import org.springframework.transaction.annotation.Transactional;
import br.com.maisprati.api.repository.ExerciseRepository;
import br.com.maisprati.api.repository.UserExerciseResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.NoSuchElementException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseGroupService {
    private final ExerciseGroupRepository exerciseGroupRepository;
    private final ExerciseGroupMapper mapper;
    private final UserExerciseResultRepository userExerciseResultRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseListRepository exerciseListRepository;

    public ExerciseGroupResponseDto criarGrupoExercicios(ExerciseGroupRequestDto exerciseGroupRequestDto) {
        ExerciseGroup exerciseGroup = mapper.toEntity(exerciseGroupRequestDto);
        ExerciseGroup exerciseResponse = exerciseGroupRepository.save(exerciseGroup);
        ExerciseGroupResponseDto exerciseGroupResponseDto = mapper.toResponse(exerciseGroup);
        return exerciseGroupResponseDto;
    }

    public List<ExerciseGroupResponseDto> buscarTodosGruposExercicios() {
        List<ExerciseGroup> exerciseGroupList = exerciseGroupRepository.findAll();
        List<ExerciseGroupResponseDto> exerciseGroupResponseDtoList = mapper.toResponseList(exerciseGroupList);
        return exerciseGroupResponseDtoList;
    }

    /**
     * Busca a entidade ExerciseGroup pelo ID e valida sua existência.
     * Usa orElseThrow() para tratamento limpo de recurso não encontrado.
     *
     * @param id O ID do grupo.
     * @return A entidade ExerciseGroup (nunca null).
     */

//    @Transactional(readOnly = true)
//    public ExerciseGroup buscarGrupoPorId(Integer id) {
//
//        // Usa findById e lança a exceção se não encontrar.
//        return exerciseGroupRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("Grupo de exercício não encontrado com ID: " + id));
//    }
//
//    public ExerciseGroupResponseDto buscarGrupoPorIdDTO(Integer id) {
//        ExerciseGroup grupo = buscarGrupoPorId(id);
//        return mapper.toResponse(grupo);
//    }
    public boolean usuarioConcluiuTodosRequisitos(User user, String listaTitulo) {
        ExerciseList lista = exerciseListRepository.findByTitulo(listaTitulo)
                .orElseThrow(() -> new RuntimeException("Lista não encontrada: " + listaTitulo));

        int totalExercicios = exerciseRepository.countByExerciseListId(lista);
        int concluidos = userExerciseResultRepository.countSuccessfulByUserAndList(user, lista);

        return totalExercicios > 0 && concluidos >= totalExercicios;
    }


    public Optional<ExerciseGroup> findByTitulo(String titulo) {
        return exerciseGroupRepository.findByTitulo(titulo);
    }

    public Optional<ExerciseGroup> buscarProximoGrupoPorOrdem(Integer ordemAtual) {
        return exerciseGroupRepository.findTopByOrdemGreaterThanOrderByOrdemAsc(ordemAtual);
    }
}
