package br.com.maisprati.api.mapper;

import br.com.maisprati.api.dto.UserAnswerRequestDto;
import br.com.maisprati.api.dto.UserAnswerResponseDto;
import br.com.maisprati.api.model.UserAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.swing.*;

@Mapper(componentModel = "spring")
public interface UserAnswerMapper {
    // De DTO Request para Entidade
    @Mapping(source = "idUsuario", target = "userId.id")
    @Mapping(source = "idExercicio", target = "exerciseId.id")
    @Mapping(source = "respostaUsuario", target = "userAnswer")
    UserAnswer toEntity(UserAnswerRequestDto userAnswerRequestDto);

    //Request -> Response
    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId.id", target = "idUsuario")
    @Mapping(source = "exerciseId.id", target = "idExercicio")
    @Mapping(source = "userAnswer", target = "respostaUsuario")
    @Mapping(source = "correctAnswer", target = "respostaCorreta")
    UserAnswerResponseDto toResponse(UserAnswer userAnswer);

}
