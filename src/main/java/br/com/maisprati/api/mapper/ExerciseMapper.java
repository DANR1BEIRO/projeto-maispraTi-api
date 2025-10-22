package br.com.maisprati.api.mapper;

import br.com.maisprati.api.dto.ExerciseRequestDto;
import br.com.maisprati.api.dto.ExerciseResponseDto;
import br.com.maisprati.api.model.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    @Mapping(source = "grupoId", target = "grupoId")
    @Mapping(source = "pergunta", target = "pergunta")
    @Mapping(source = "alternativas", target = "alternativas")
    @Mapping(source = "respostaCorreta", target = "respostaCorreta")
    Exercise toEntity(ExerciseRequestDto exerciseRequestDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "grupoId", target = "grupoId")
    @Mapping(source = "pergunta", target = "pergunta")
    @Mapping(source = "alternativas", target = "alternativas")
    @Mapping(source = "respostaCorreta", target = "respostaCorreta")
    ExerciseResponseDto toResponse(Exercise exercise);
}
