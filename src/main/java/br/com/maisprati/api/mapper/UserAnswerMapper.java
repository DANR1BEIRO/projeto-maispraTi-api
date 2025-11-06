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
    @Mapping(source = "idUsuario", target = "userId")
    @Mapping(source = "idExercicio", target = "exerciseId")
    @Mapping(source = "userAnswer", target = "respostaUsuario")
    UserAnswer toEntity(UserAnswerRequestDto userAnswerRequestDto);

    //Request -> Response
    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "idUsuario")
    @Mapping(source = "exerciseId", target = "idExercicio")
    @Mapping(source = "userAnswer", target = "respostaUsuario")
    UserAnswerResponseDto toResponse(UserAnswer userAnswer);

}
