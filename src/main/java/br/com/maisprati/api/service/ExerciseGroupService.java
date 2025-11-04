package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.ExerciseGroupRequestDto;
import br.com.maisprati.api.dto.ExerciseGroupResponseDto;

import br.com.maisprati.api.mapper.ExerciseGroupMapper;
import br.com.maisprati.api.model.ExerciseGroup;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.repository.ExerciseGroupRepository;
import br.com.maisprati.api.repository.ExerciseRepository;
import br.com.maisprati.api.repository.UserExerciseResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<String> calcularGruposBloqueados(User user, List<String> gruposConcluidos) {
        List<ExerciseGroup> todosGrupos = exerciseGroupRepository.findAll();

        return todosGrupos.stream()
                .map(ExerciseGroup::getTitle)
                .filter(titulo -> !gruposConcluidos.contains(titulo))
                .toList();
    }

}
