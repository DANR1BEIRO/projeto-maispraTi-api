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

@Service
@RequiredArgsConstructor
public class UserAnswerService {
//O usuário irá responser via serviço /submit, e eu irei salvar as respostar do usuário no userAnsweServic
// Este serviço irá servir para o progresso validar se está OK?
    private final UserAnswerRepository userAnswerRepository;
    private final UserAnswerMapper userAnswerMapper;
    private final ExerciseService exerciseService;
    public UserAnswerResponseDto criarResposta(UserAnswerRequestDto userAnswerRequestDto){
        UserAnswer userAnswer = userAnswerMapper.toEntity(userAnswerRequestDto);

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
        Optional<UserAnswer> userAnswer = userAnswerRepository.findById(id);
        UserAnswer userAnswerResponse = userAnswerRepository.getReferenceById(id);
        UserAnswerResponseDto userAnswerResponseDto = userAnswerMapper.toResponse(userAnswerResponse);
        return userAnswerResponseDto;
    }
}
