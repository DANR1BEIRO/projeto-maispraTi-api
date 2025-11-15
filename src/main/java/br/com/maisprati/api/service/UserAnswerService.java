package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.ExerciseResponseDto;
import br.com.maisprati.api.dto.UserAnswerRequestDto;
import br.com.maisprati.api.dto.UserAnswerResponseDto;
import br.com.maisprati.api.mapper.UserAnswerMapper;
import br.com.maisprati.api.model.Exercise;
import br.com.maisprati.api.model.ExerciseList;
import br.com.maisprati.api.model.UserAnswer;
import br.com.maisprati.api.repository.ExerciseListRepository;
import br.com.maisprati.api.repository.ExerciseRepository;
import br.com.maisprati.api.repository.UserAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static br.com.maisprati.api.enuns.ProgressStatusEnum.CONCLUIDO;

@Service
@RequiredArgsConstructor
public class UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;
    private final UserAnswerMapper userAnswerMapper;
    private final ExerciseService exerciseService;
    private final ExerciseListRepository exerciseListRepository;
    private final ExerciseRepository exerciseRepository;

    public UserAnswerResponseDto criarResposta(UserAnswerRequestDto userAnswerRequestDto){
        UserAnswer userAnswerExisting = userAnswerRepository
                .findByUserId_IdAndExerciseId_Id(
                        userAnswerRequestDto.getIdUsuario(),
                        userAnswerRequestDto.getIdExercicio()
                );
        UserAnswer userAnswer;

        ExerciseResponseDto exercise = exerciseService.buscarExercicio(userAnswerRequestDto.getIdExercicio());

        boolean correta = exercise.getRespostaCorreta().equals(userAnswerRequestDto.getRespostaUsuario());

//        ExerciseList exerciseList = new ExerciseList();
//        exerciseList.setId(exercise.getListaId());
//        userAnswer.setExerciseListId(exerciseList);

        if(userAnswerExisting != null){
            userAnswer = userAnswerExisting;
            userAnswer.setUserAnswer(userAnswerRequestDto.getRespostaUsuario());
            userAnswer.setCorrectAnswer(correta);

        } else {
            userAnswer = userAnswerMapper.toEntity(userAnswerRequestDto);
            userAnswer.setCorrectAnswer(correta);
            userAnswer.setStatus(CONCLUIDO);
        }
        UserAnswer savedUserAnswer = userAnswerRepository.save(userAnswer);
//            Integer nextExercise = this.nextExercise(userAnswer.getId());
        UserAnswerResponseDto userAnswerResponseDto = userAnswerMapper.toResponse(savedUserAnswer, exercise);

        if(correta){
            userAnswerResponseDto.setMenssagem("Parabéns! Resposta correta!");
        } else {
            userAnswerResponseDto.setMenssagem("Que pena! A resposta está incorreta.");
        }

        return userAnswerResponseDto;
    }

    public UserAnswerResponseDto buscarResposta(Integer id){
        Optional<UserAnswer> userAnswer = userAnswerRepository.findFirstByExerciseId_Id(id);
//        UserAnswer userAnswerResponse = userAnswerRepository.getReferenceById(id);
        UserAnswerResponseDto userAnswerResponseDto = userAnswerMapper.toResponse(userAnswer.get());
        return userAnswerResponseDto;
    }

    //TODO> Retornar campos de id do exercicio. Até o momento retornar msg null e resposta correta null.
    public List<UserAnswerResponseDto> getUserAnswers(Integer id){
        List<UserAnswer> userAnswerList = userAnswerRepository.findAllByUserId_IdAndCorrectAnswerTrue(id);
        List<UserAnswerResponseDto> userAnswerResponseDto = userAnswerMapper.toResponseList(userAnswerList);
        return userAnswerResponseDto;
    }
    //verificar pelo id do usuário qual o último id de exercicio da lista que ele respondeu
    //Então serão 2 validações: separar a lista e depois separar o exercicio
    //vou criar um serviço que chama pelo id do usuário
    //depois vou criar um serviço que valida o próximo item da lista
    //posso pegar as coisas que o daniel já fez e o que eu fiz na segunda

    //Agora eu tenho um serviço que retorna todos as respostas de um usuário
    //Agora tenho que pegar o id da lista + o id do exercicio, e mudar o status do item para disponível
    // Olho para dentro da lista de exercicios qual é o primeiro item disponível

    //Vou fazer um find que busque pelo id da lista, id do exercicio e se a resposta é true
//    TODO: criar o find mencionado acima. Já coloquei a lista a ser setadada em userAnswer, agora é ajustar o progresso
//     fazendo a verificação se dentro de uma lista ainda existe algum exercicio não preenchido

    public Integer nextExercise(Integer userId) {
        Integer nextExerciseId = null;
        List<UserAnswer> userAnswers = userAnswerRepository.findAllByUserId_Id(userId);

        Set<Integer> listaIds = userAnswers.stream()
                .map(ua -> ua.getExerciseListId().getId())
                .collect(Collectors.toSet());

        for (Integer listaId : listaIds) {

            ExerciseList exerciseList = exerciseListRepository.findById(listaId)
                    .orElseThrow(() -> new RuntimeException("Lista não encontrada"));

            // Exercícios concluídos
            Set<Integer> completed = userAnswerRepository
                    .findAllByUserId_IdAndExerciseListId_IdOrderByExerciseId_IdAsc(userId, listaId)
                    .stream()
                    .map(ua -> ua.getExerciseId().getId())
                    .collect(Collectors.toSet());

            // Exercícios da lista original (ordenados)
            List<Integer> original = exerciseList.getExercicios().stream()
                    .map(Exercise::getId)
                    .sorted()
                    .toList();

            // Exercícios faltantes
            List<Integer> missing = original.stream()
                    .filter(id -> !completed.contains(id))
                    .toList();

            if (!missing.isEmpty()) {
                nextExerciseId = missing.get(0);
                // montar DTO aqui
            }


        }
        return nextExerciseId;
    }
}


//    public UserAnswerResponseDto nextExercise(Integer userId){
//        //Aqui eu peguei todos as respotas de usuários
//        List<UserAnswer> userAnswers = userAnswerRepository.findAllByUserId_Id(userId);
//        //Aqui crio um HashSet para armazenar as listas, sem repetir os ids
//        Set<Integer> listaIds = new HashSet<>();
//        for (UserAnswer ua : userAnswers) {
//            listaIds.add(ua.getExerciseListId().getId());
//        }
//        //instancio a classe exerciseList
//        ExerciseList exerciseList = new ExerciseList();
//
//
//        for(Integer listaId: listaIds ){
//            //Aqui eu coloco os ids de exercicios da lista em ordem
//            List<UserAnswer> exercisesCompleted = userAnswerRepository
//                    .findAllByUserId_IdAndExerciseListId_IdOrderByExercise_IdAsc(userId, listaId);
//            //aqui eu tentei colocar os exercicios completados em uma lista, mas não é possível
//            for (int i = 0; i < exercisesCompleted.size(); i++){
//                List<Integer> integers = exercisesCompleted.get(i);
//
//            }
//
//            //Agora tenho que comparar se na lista original possui a mesma quantidade de exercicios que o exercisesCompleted
//            //se for diferente quer dizer que a lista não foi completada
//            //então tenho que pegar o próximo item (exercise) da lista de exerciseListId de forma ordenada
//            //e passar para disponível
//            if (exercisesCompleted.size() != exerciseList.getExercicios().size()){
//                //a ideia aqui era pegar os ids que não foram completados (são iguais ao que tem na lista)
//                //e colocar em uma lista, mas deu erro
//                for(int i =0; i < exercisesCompleted.size(); i++){
//                    if (exercisesCompleted.get(i) != exerciseList.getExercicios().get(i))
//                }
//            }
//
//        }

//        //aqui talvez não faça sentido
//        Set<Integer> exercisesIds = new HashSet<>();
//        for(UserAnswer userAnswer : userAnswers ){
//            exercisesIds.add(userAnswer.getExerciseId().getId());
//        }
//
//        //vou pegar o id do exercicio
//        //pegar quantos exercicios tem
//        //verificar quantos exercicios daquele id existem
//
//        //Agora tenho que verificar se na lista tem algum item incompleto
//        for(int i = 0; i < listaIds.size(); i++ ){
//            if(listaIds.)
//        }
//
//        List<ExerciseList> exerciseLists = userAnswerRepository.findAllByUserId_IdAndExerciseListId_IdOrderByIdAsc()


//}
