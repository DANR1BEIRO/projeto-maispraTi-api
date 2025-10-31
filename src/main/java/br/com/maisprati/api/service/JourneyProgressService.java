package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.ExerciseGroupResponseDto;
import br.com.maisprati.api.dto.JourneyAdvanceRequestDto;
import br.com.maisprati.api.dto.JourneyProgressResponseDto;
import br.com.maisprati.api.model.ExerciseGroup; // << Adicionado
import br.com.maisprati.api.model.JourneyProgress;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.repository.JourneyProgressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional; // << Adicionado
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true) // somente leitura por padrão
public class JourneyProgressService {

    private final JourneyProgressRepository progressRepository;
    private final UserService userService;
    private final ExerciseGroupService exerciseGroupService;

    public JourneyProgressService(
            JourneyProgressRepository progressRepository,
            UserService userService,
            ExerciseGroupService exerciseGroupService) {
        this.progressRepository = progressRepository;
        this.userService = userService;
        this.exerciseGroupService = exerciseGroupService;
    }


    private boolean validarRequisitosCompletos(User user, String grupoConcluido) {
        return exerciseGroupService.usuarioConcluiuTodosRequisitos(user, grupoConcluido);
    }

    private String determinarProximoGrupo(String grupoAtual) {

        // 1. Busca o grupo que foi concluído para pegar a ordem
        Optional<ExerciseGroup> grupoConcluidoOpt = exerciseGroupService.findByTitulo(grupoAtual);

        // Se não achou (dado inconsistente), considera a trilha concluída.
        if (grupoConcluidoOpt.isEmpty()) {
            return "Trilha Concluída";
        }

        Integer ordemAtual = grupoConcluidoOpt.get().getSequence();

        Optional<ExerciseGroup> proximoGrupoOpt = exerciseGroupService.buscarProximoGrupoPorOrdem(ordemAtual);

        // Se não houver mais grupos com ordem maior, a trilha está concluída.
        if (proximoGrupoOpt.isEmpty()) {
            return "Trilha Concluída";
        }

        return proximoGrupoOpt.get().getTitle();
    }

    private List<String> calcularGruposBloqueados(JourneyProgress progresso) {
        List<ExerciseGroupResponseDto> todosGruposDto = exerciseGroupService.buscarTodosGruposExercicios();

        List<String> todosOsGruposDoSistema = todosGruposDto.stream()
                .map(ExerciseGroupResponseDto::getTitulo)
                .collect(Collectors.toUnmodifiableList());

        List<String> concluidos = progresso.getGruposConcluidos();
        String atual = progresso.getGrupoAtual();

        return todosOsGruposDoSistema.stream()
                .filter(grupo -> !concluidos.contains(grupo))
                .filter(grupo -> !grupo.equals(atual))
                .collect(Collectors.toUnmodifiableList());
    }

    public JourneyProgressResponseDto consultarProgresso(Integer userId) {

        // 1 Busca do usuario
        User user = userService.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        // busca do progresso
        JourneyProgress progresso = progressRepository.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Progresso de jornada não encontrado para o usuário."
                ));

        // lista de grupos bloqueados
        List<String> gruposBloqueados = calcularGruposBloqueados(progresso);

        return JourneyProgressResponseDto.builder()
                .grupoAtual(progresso.getGrupoAtual())
                .gruposConcluidos(progresso.getGruposConcluidos())
                .proximosGruposBloqueados(gruposBloqueados)
                .build();
    }

    /**
     * Permite ao usuário avançar para o próximo grupo de aprendizado.
     */
    @Transactional
    public JourneyProgressResponseDto avancarProgresso(Integer userId, JourneyAdvanceRequestDto dto) {

        User user = userService.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        JourneyProgress progresso = progressRepository.findByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Progresso de jornada não encontrado."));

        // verifica se o grupo do dto é o grupo atual do user
        String grupoConcluido = dto.getGrupoTentandoConcluir();
        if (!grupoConcluido.equals(progresso.getGrupoAtual())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O grupo que está tentando concluir ('" + grupoConcluido + "') não é o grupo atual do usuário ('" + progresso.getGrupoAtual() + "')."
            );
        }

        if (!validarRequisitosCompletos(user, grupoConcluido)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "O usuário não completou todos os exercícios necessários para avançar do grupo: " + grupoConcluido
            );
        }

        progresso.getGruposConcluidos().add(progresso.getGrupoAtual());

        String proximoGrupo = determinarProximoGrupo(progresso.getGrupoAtual());
        progresso.setGrupoAtual(proximoGrupo);

        progressRepository.save(progresso);

        List<String> gruposBloqueados = calcularGruposBloqueados(progresso);
        return JourneyProgressResponseDto.builder()
                .grupoAtual(progresso.getGrupoAtual())
                .gruposConcluidos(progresso.getGruposConcluidos())
                .proximosGruposBloqueados(gruposBloqueados)
                .build();
    }
}