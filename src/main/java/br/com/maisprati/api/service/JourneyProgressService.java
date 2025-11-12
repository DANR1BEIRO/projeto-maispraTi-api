package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.JourneyAdvanceRequestDto;
import br.com.maisprati.api.dto.JourneyProgressResponseDto;
import br.com.maisprati.api.model.ExerciseList;
import br.com.maisprati.api.model.JourneyProgress;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.repository.JourneyProgressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class JourneyProgressService {

    private final JourneyProgressRepository progressRepository;
    private final UserService userService;
    private final ExerciseListService exerciseListService;

    public JourneyProgressService(
            JourneyProgressRepository progressRepository,
            UserService userService,
            ExerciseListService exerciseListService
    ) {
        this.progressRepository = progressRepository;
        this.userService = userService;
        this.exerciseListService = exerciseListService;
    }

    private boolean validarRequisitosCompletos(User user, String tituloLista) {
        return exerciseListService.usuarioConcluiuTodosRequisitos(user, tituloLista);
    }

    private String determinarProximaLista(String listaAtual) {

        ExerciseList lista = exerciseListService.findByTitulo(listaAtual)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Lista não encontrada: " + listaAtual));

        return exerciseListService.buscarProximaListaPorOrdem(lista.getId())
                .map(ExerciseList::getTitulo)
                .orElse("Trilha Concluída");
    }

    private List<String> calcularListasBloqueadas(JourneyProgress progresso) {

        List<String> todas = exerciseListService.buscarTodasAsListas()
                .stream()
                .map(dto -> dto.getTitulo())
                .toList();

        return todas.stream()
                .filter(titulo -> !progresso.getListasDeExerciciosConcluidos().contains(titulo))
                .filter(titulo -> !titulo.equals(progresso.getListaAtual()))
                .toList();
    }

    public JourneyProgressResponseDto consultarProgresso(Integer userId) {

        User user = userService.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        JourneyProgress progresso = progressRepository.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Progresso não encontrado."));

        List<String> bloqueadas = calcularListasBloqueadas(progresso);

        return JourneyProgressResponseDto.builder()
                .listaAtual(progresso.getListaAtual())
                .listasConcluidos(progresso.getListasDeExerciciosConcluidos())
                .proximasListasBloqueadas(bloqueadas)
                .build();
    }

    @Transactional
    public JourneyProgressResponseDto avancarProgresso(Integer userId, JourneyAdvanceRequestDto dto) {

        User user = userService.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        JourneyProgress progresso = progressRepository.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Progresso não encontrado"));

        String listaTentandoConcluir = dto.getListaTentandoConcluir();

        if (!listaTentandoConcluir.equals(progresso.getListaAtual())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A lista enviada não corresponde à lista atual do usuário."
            );
        }

        if (!validarRequisitosCompletos(user, listaTentandoConcluir)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "O usuário ainda não concluiu todos os exercícios desta lista."
            );
        }

        progresso.getListasDeExerciciosConcluidos().add(progresso.getListaAtual());

        String proxima = determinarProximaLista(progresso.getListaAtual());
        progresso.setListaAtual(proxima);

        progressRepository.save(progresso);

        return JourneyProgressResponseDto.builder()
                .listaAtual(progresso.getListaAtual())
                .listasConcluidos(progresso.getListasDeExerciciosConcluidos())
                .proximasListasBloqueadas(calcularListasBloqueadas(progresso))
                .build();
    }
}
