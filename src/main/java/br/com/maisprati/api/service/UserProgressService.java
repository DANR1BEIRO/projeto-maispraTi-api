//package br.com.maisprati.api.service;
//
//import br.com.maisprati.api.dto.UserAnswerResponseDto;
//import br.com.maisprati.api.dto.UserProgressResponseDto;
//import br.com.maisprati.api.enuns.ProgressStatusEnum;
//import br.com.maisprati.api.mapper.UserProgressMapper;
//import br.com.maisprati.api.model.Exercise;
//import br.com.maisprati.api.model.ExerciseList;
//import br.com.maisprati.api.model.User;
//import br.com.maisprati.api.model.UserProgress;
//import br.com.maisprati.api.repository.ExerciseListRepository;
//import br.com.maisprati.api.repository.UserProgressRepository;
//import br.com.maisprati.api.repository.UserRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class UserProgressService {
//    private final ExerciseListRepository exerciseListRepository;
//    private final UserProgressRepository userProgressRepository;
//    private final UserRepository userRepository;
//    private final UserProgressMapper mapper;
//
//    private final UserAnswerService userAnswerService;
//// re
//
//    public UserProgressResponseDto getNextExercise(Integer userId){
//        Optional<UserProgress> disponivel = userProgressRepository
//                .findFirstByUser_IdAndStatusOrderByExercise_OrdemAsc(userId, ProgressStatusEnum.DISPONIVEL);
//
//        if(disponivel.isPresent()){
//            return mapper.toResponse(disponivel.get());
//        }
//
//        Optional<UserProgress> emProgresso =
//                userProgressRepository.findFirstByUser_IdAndStatusOrderByExercise_OrdemAsc(
//                        userId, ProgressStatusEnum.CONCLUIDO
//                );
//
//        if(emProgresso.isPresent()){
//            return  mapper.toResponse(emProgresso.get());
//        }
//
//        List<ExerciseList> listas = exerciseListRepository.findAllByIdByOrdemAsc();
//
//        for(ExerciseList lista : listas){
//            List<Exercise> exercisesOrdered = lista.getExercicios().stream()
//                    .sorted((a,b) -> a.getId() - b.getId())
//                    .toList();
//
//            for(Exercise exercise : exercisesOrdered){
//                Optional<UserProgress> progress = userProgressRepository
//                        .findByUser_IdAndExercise_Id(userId, exercise.getId());
//
//                if(progress.isEmpty() || progress.get().getStatus() != ProgressStatusEnum.CONCLUIDO) {
//                    UserProgress registro;
//
//                    UserAnswerResponseDto userAnswerResponseDto = userAnswerService.buscarResposta(exercise.getId());
//
//                    if(progress.isPresent()){
//                        registro = progress.get();
//                    } else {
//                        registro = new UserProgress();
//                        User referenceById = userRepository.getReferenceById(userId);
//                        registro.setUser(referenceById);
//                        registro.setExercise(exercise);
//                        registro.setStatus(ProgressStatusEnum.DISPONIVEL);
//                        registro.setRespondidoCorretamente(userAnswerResponseDto.getRespostaCorreta());
//                        registro = userProgressRepository.save(registro);
//                    }
//
//                    return mapper.toResponse(registro);
//                }
//            }
//        }
//
//        return null;
//    }
//
//    @Transactional
//    public void finishExercise(Integer userId, Integer exerciseId) {
//        UserProgress atual = userProgressRepository.findByUser_IdAndExercise_Id(userId, exerciseId)
//                .orElseThrow(() -> new RuntimeException("progresso nao encontrado"));
//
//
//        atual.setStatus(ProgressStatusEnum.CONCLUIDO);
//        atual.setRespondidoCorretamente(true);
//        userProgressRepository.save(atual);
//
//
//        Exercise exercicioAtual = atual.getExercise();
//        ExerciseList listaAtual = exercicioAtual.getExerciseListId();
//
//
//        Exercise proximo = listaAtual.getExercicios().stream()
//                .filter(e -> e.getId() > exercicioAtual.getId())
//                .sorted((a, b) -> Integer.compare(a.getId(), b.getId()))
//                .findFirst()
//                .orElse(null);
//
//
//        if (proximo != null) {
//            UserProgress up = userProgressRepository.findByUser_IdAndExercise_Id(userId, proximo.getId()).orElseThrow();
//            up.setStatus(ProgressStatusEnum.DISPONIVEL);
//            userProgressRepository.save(up);
//            return;
//        }
//
//
////        ExerciseList proximaLista = exerciseListRepository.findAllByOrderByOrdemAsc(listaAtual.getOrdem() + 1).orElse(null);
//        ExerciseList proximaLista = exerciseListRepository.findById(listaAtual.getId() + 1).orElse(null);
//
//        if (proximaLista != null && !proximaLista.getExercicios().isEmpty()) {
//            Exercise primeiroProxLista = proximaLista.getExercicios().stream()
//                    .sorted((a, b) -> Integer.compare(a.getId(), b.getId()))
//                    .findFirst()
//                    .orElseThrow();
//
//
//            UserProgress up = userProgressRepository.findByUser_IdAndExercise_Id(userId, primeiroProxLista.getId()).orElseThrow();
//            up.setStatus(ProgressStatusEnum.DISPONIVEL);
//            userProgressRepository.save(up);
//        }
//    }
//}
//
//
