package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.ExerciseResponseDto;
import br.com.maisprati.api.dto.UserAnswerRequestDto;
import br.com.maisprati.api.dto.UserAnswerResponseDto;
import br.com.maisprati.api.mapper.UserAnswerMapper;
import br.com.maisprati.api.model.ExerciseList;
import br.com.maisprati.api.model.UserAnswer;
import br.com.maisprati.api.repository.UserAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.maisprati.api.enuns.ProgressStatusEnum.CONCLUIDO;

@Service
@RequiredArgsConstructor
public class UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;
    private final UserAnswerMapper userAnswerMapper;
    private final ExerciseService exerciseService;
    public UserAnswerResponseDto criarResposta(UserAnswerRequestDto userAnswerRequestDto){
        UserAnswer userAnswer = userAnswerMapper.toEntity(userAnswerRequestDto);

        ExerciseResponseDto exercise = exerciseService.buscarExercicio(userAnswerRequestDto.getIdExercicio());

        boolean correta = exercise.getRespostaCorreta().equals(userAnswerRequestDto.getRespostaUsuario());

        userAnswer.setCorrectAnswer(correta);

        userAnswer.setStatus(CONCLUIDO);

        ExerciseList exerciseList = new ExerciseList();
        exerciseList.setId(exercise.getListaId());
        userAnswer.setExerciseListId(exerciseList);

        UserAnswer savedUserAnswer = userAnswerRepository.save(userAnswer);

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
//    public UserAnswerResponseDto nextExercise(){
//
//    }
}
