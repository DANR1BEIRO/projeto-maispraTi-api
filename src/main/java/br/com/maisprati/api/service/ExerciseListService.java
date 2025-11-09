package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.ExerciseListRequestDto;
import br.com.maisprati.api.dto.ExerciseListResponseDto;
import br.com.maisprati.api.mapper.ExerciseListMapper;
import br.com.maisprati.api.model.ExerciseList;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.repository.ExerciseListRepository;
import br.com.maisprati.api.repository.ExerciseRepository;
import br.com.maisprati.api.repository.UserExerciseResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseListService {

    private final ExerciseListRepository exerciseListRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserExerciseResultRepository userExerciseResultRepository;
    private final ExerciseListMapper mapper;

    public ExerciseListResponseDto criarLista(ExerciseListRequestDto dto) {
        ExerciseList entity = mapper.toEntity(dto);
        exerciseListRepository.save(entity);
        return mapper.toResponse(entity);
    }

    public List<ExerciseListResponseDto> buscarTodasAsListas() {
        return mapper.toResponseList(exerciseListRepository.findAll());
    }

    public Optional<ExerciseList> findByTitulo(String titulo) {
        return exerciseListRepository.findByTitulo(titulo);
    }

    public Optional<ExerciseList> buscarProximaListaPorOrdem(Integer ordemAtual) {
        return exerciseListRepository.buscarProximaLista(ordemAtual);
    }
    public boolean usuarioConcluiuTodosRequisitos(User user, String tituloLista) {

        ExerciseList lista = exerciseListRepository.findByTitulo(tituloLista)
                .orElseThrow(() -> new RuntimeException("Lista não encontrada: " + tituloLista));

        int totalExercicios = exerciseRepository.countByExerciseListId(lista);
        int concluidos = userExerciseResultRepository.countSuccessfulByUserAndList(user, lista);

        return totalExercicios > 0 && concluidos >= totalExercicios;
    }
}
