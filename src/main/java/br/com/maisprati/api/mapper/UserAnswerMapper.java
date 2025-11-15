package br.com.maisprati.api.mapper;

import br.com.maisprati.api.dto.ExerciseResponseDto;
import br.com.maisprati.api.dto.UserAnswerRequestDto;
import br.com.maisprati.api.dto.UserAnswerResponseDto;
import br.com.maisprati.api.model.UserAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserAnswerMapper {
    @Mapping(source = "idUsuario", target = "userId.id")
    @Mapping(source = "idExercicio", target = "exerciseId.id")
    @Mapping(source = "respostaUsuario", target = "userAnswer")
    UserAnswer toEntity(UserAnswerRequestDto userAnswerRequestDto);

    @Mapping(source = "correctAnswer", target = "respostaCorreta")
    UserAnswerResponseDto toResponse(UserAnswer userAnswer);

    @Mapping(source = "userAnswer.correctAnswer", target = "respostaCorreta")
    @Mapping(source = "exercise.respostaCorreta", target = "respostaCorretaExercise")
    UserAnswerResponseDto toResponse(UserAnswer userAnswer, ExerciseResponseDto exercise);

    List<UserAnswerResponseDto> toResponseList(List<UserAnswer> userAnswerList);

}