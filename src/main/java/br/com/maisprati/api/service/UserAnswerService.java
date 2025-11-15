package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.ExerciseResponseDto;
import br.com.maisprati.api.dto.UserAnswerRequestDto;
import br.com.maisprati.api.dto.UserAnswerResponseDto;
import br.com.maisprati.api.mapper.UserAnswerMapper;
import br.com.maisprati.api.model.UserAnswer;
import br.com.maisprati.api.repository.ExerciseListRepository;
import br.com.maisprati.api.repository.ExerciseRepository;
import br.com.maisprati.api.repository.UserAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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

        if(userAnswerExisting != null){
            userAnswer = userAnswerExisting;
            userAnswer.setUserAnswer(userAnswerRequestDto.getRespostaUsuario());
            userAnswer.setCorrectAnswer(correta);

        } else {
            userAnswer = userAnswerMapper.toEntity(userAnswerRequestDto);
            userAnswer.setCorrectAnswer(correta);
        }
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
        UserAnswerResponseDto userAnswerResponseDto = userAnswerMapper.toResponse(userAnswer.get());
        return userAnswerResponseDto;
    }

    public List<UserAnswerResponseDto> getUserAnswers(Integer id){
        List<UserAnswer> userAnswerList = userAnswerRepository.findAllByUserId_IdAndCorrectAnswerTrue(id);
        List<UserAnswerResponseDto> userAnswerResponseDto = userAnswerMapper.toResponseList(userAnswerList);
        return userAnswerResponseDto;
    }

}