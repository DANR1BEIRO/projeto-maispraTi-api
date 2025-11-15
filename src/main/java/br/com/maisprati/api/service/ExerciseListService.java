package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.ExerciseListRequestDto;
import br.com.maisprati.api.dto.ExerciseListResponseDto;
import br.com.maisprati.api.mapper.ExerciseListMapper;
import br.com.maisprati.api.model.ExerciseList;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.model.UserAnswer;
import br.com.maisprati.api.repository.ExerciseListRepository;
import br.com.maisprati.api.repository.ExerciseRepository;
import br.com.maisprati.api.repository.UserAnswerRepository;
import br.com.maisprati.api.repository.UserExerciseResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ExerciseListService {

    private final ExerciseListRepository exerciseListRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserExerciseResultRepository userExerciseResultRepository;

    private final UserAnswerRepository userAnswerRepository;
    private final ExerciseListMapper mapper;

    public ExerciseListResponseDto criarLista(ExerciseListRequestDto dto) {
        ExerciseList entity = mapper.toEntity(dto);
        exerciseListRepository.save(entity);
        return mapper.toResponse(entity);
    }

    public List<ExerciseListResponseDto> buscarTodasAsListas() {
        return mapper.toResponseList(exerciseListRepository.findAll());
    }

    public List<Map<String, Object>> buscarTodasAsListas(Integer idUsuario) {
        return buscarListas(idUsuario);
    }

//    public List<Map<String, Object>> buscarListas(Integer idUsuario) {
//        List<ExerciseListResponseDto> todasListas = mapper.toResponseList(exerciseListRepository.findAll());
//
//        List<Map<String, Object>> listaRetorno = new ArrayList<>();
//
//        for (ExerciseListResponseDto lista: todasListas) {
//            List<Map<String, Object>> listaExercicioRetorno = new ArrayList<>();
//            Map<String, Object> retorno = new HashMap<>();
//            for (Integer idExercicio: lista.getExerciciosIds()) {
//                UserAnswer byUserId_idAndExerciseId_id = userAnswerRepository.findByUserId_IdAndExerciseId_Id(idUsuario, idExercicio);
//                if (byUserId_idAndExerciseId_id != null) {
//                    listaExercicioRetorno.add(Map.of("id", idExercicio, "respondido", Boolean.TRUE, "disponivel", Boolean.TRUE));
//                } else {
//                    listaExercicioRetorno.add(Map.of("id", idExercicio, "respondido", Boolean.FALSE, "disponivel", Boolean.FALSE));
//                }
//            }
//
//            retorno.put("exercicios", listaExercicioRetorno);
//            retorno.put("id;", lista.getId());
//            retorno.put("titulo;", lista.getTitulo());
//            retorno.put("descricao;", lista.getDescricao());
//            listaRetorno.add(retorno);
//        }
//
//        return listaRetorno;
//    }

    public List<Map<String, Object>> buscarListas(Integer idUsuario) {
        List<ExerciseListResponseDto> todasListas = mapper.toResponseList(exerciseListRepository.findAll());

        List<Map<String, Object>> listaRetorno = new ArrayList<>();

        boolean listaAnteriorCompleta = true; // A primeira lista começa como disponível

        for (ExerciseListResponseDto lista : todasListas) {

            boolean proximoExercicioDisponivelEncontrado = false;
            boolean listaDisponivel = listaAnteriorCompleta;

            List<Map<String, Object>> listaExercicioRetorno = new ArrayList<>();
            Map<String, Object> retorno = new HashMap<>();

            boolean todosRespondidos = true;

            // Ordena a lista interna pelo ID
            List<Integer> idsOrdenados = new ArrayList<>(lista.getExerciciosIds());
            Collections.sort(idsOrdenados);

            for (Integer idExercicio : idsOrdenados) {

                UserAnswer userAnswer =
                        userAnswerRepository.findByUserId_IdAndExerciseId_Id(idUsuario, idExercicio);

                boolean respondido = (userAnswer != null);
//                boolean disponivelExercicio = false;
                boolean disponivelExercicio;
                if (!respondido) {
                    todosRespondidos = false;
                }

                //AQUI
                if (respondido) {
                    // Se está respondido, ele é disponível SEMPRE
                    disponivelExercicio = true;

                } else if (listaDisponivel && !proximoExercicioDisponivelEncontrado) {
                    // Primeiro não respondido da lista disponível
                    disponivelExercicio = true;
                    proximoExercicioDisponivelEncontrado = true;

                } else {
                    // Demais não respondidos
                    disponivelExercicio = false;
                }
//                if (listaDisponivel) {
//                    if (!respondido && !proximoExercicioDisponivelEncontrado) {
//                        disponivelExercicio = true;
//                        proximoExercicioDisponivelEncontrado = true;
//                    }
//                }

                listaExercicioRetorno.add(
                        Map.of(
                                "id", idExercicio,
                                "respondido", respondido,
                                "disponivel", disponivelExercicio
                        )
                );
            }

            retorno.put("disponivel", listaDisponivel);
            retorno.put("exercicios", listaExercicioRetorno);
            retorno.put("id", lista.getId());
            retorno.put("titulo", lista.getTitulo());
            retorno.put("descricao", lista.getDescricao());

            listaRetorno.add(retorno);

            listaAnteriorCompleta = todosRespondidos;
        }

        return listaRetorno;
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
