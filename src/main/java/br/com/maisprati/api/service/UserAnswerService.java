package br.com.maisprati.api.service;

import br.com.maisprati.api.dto.ExerciseResponseDto;
import br.com.maisprati.api.dto.UserAnswerRequestDto;
import br.com.maisprati.api.dto.UserAnswerResponseDto;
import br.com.maisprati.api.mapper.UserAnswerMapper;
import br.com.maisprati.api.model.Exercise;
import br.com.maisprati.api.model.User;
import br.com.maisprati.api.model.UserAnswer;
import br.com.maisprati.api.repository.UserAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        userAnswer.setStatus(CONCLUIDO);

        ExerciseResponseDto exercise = exerciseService.buscarExercicio(userAnswerRequestDto.getIdExercicio());

        boolean correta = exercise.getRespostaCorreta().equals(userAnswerRequestDto.getRespostaUsuario());

        userAnswer.setCorrectAnswer(correta);

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
}
