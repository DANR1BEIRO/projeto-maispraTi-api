package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.ExerciseGroupRequestDto;
import br.com.maisprati.api.dto.ExerciseGroupResponseDto;

import br.com.maisprati.api.mapper.ExerciseGroupMapper;
import br.com.maisprati.api.model.ExerciseGroup;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.model.UserExerciseResult;
import br.com.maisprati.api.repository.ExerciseGroupRepository;
<<<<<<< HEAD
import org.springframework.transaction.annotation.Transactional;
=======
import br.com.maisprati.api.repository.ExerciseRepository;
import br.com.maisprati.api.repository.UserExerciseResultRepository;
>>>>>>> fabf9f0 (feat(journey): Implementa fluxo completo de progresso e avanco de usuario)
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
<<<<<<< HEAD
import java.util.NoSuchElementException;
=======
import java.util.Optional;
>>>>>>> fabf9f0 (feat(journey): Implementa fluxo completo de progresso e avanco de usuario)

@Service
@RequiredArgsConstructor
public class ExerciseGroupService {
    private final ExerciseGroupRepository exerciseGroupRepository;
    private final ExerciseGroupMapper mapper;
    private final UserExerciseResultRepository userExerciseResultRepository;
    private final ExerciseRepository exerciseRepository;

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

<<<<<<< HEAD
    /**
     * Busca a entidade ExerciseGroup pelo ID e valida sua existência.
     * Usa orElseThrow() para tratamento limpo de recurso não encontrado.
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

=======
    public boolean usuarioConcluiuTodosRequisitos(User user, String grupoTitulo) {
        ExerciseGroup grupo = exerciseGroupRepository.findByTitulo(grupoTitulo)
                .orElseThrow(() -> new RuntimeException("Grupo de exercício não encontrado pelo título: " + grupoTitulo));

        int totalExerciciosNoGrupo = exerciseRepository.countByGrupo(grupo);
        int concluidosPeloUsuario = userExerciseResultRepository.countSuccessfulByUserAndGroup(user, grupo);

        return totalExerciciosNoGrupo > 0 && concluidosPeloUsuario >= totalExerciciosNoGrupo;
    }

    public Optional<ExerciseGroup> findByTitulo(String titulo) {
        return exerciseGroupRepository.findByTitulo(titulo);
    }

    public Optional<ExerciseGroup> buscarProximoGrupoPorOrdem(Integer ordemAtual) {
        return exerciseGroupRepository.findTopByOrdemGreaterThanOrderByOrdemAsc(ordemAtual);
    }
>>>>>>> fabf9f0 (feat(journey): Implementa fluxo completo de progresso e avanco de usuario)
}
