package br.com.maisprati.api.mapper;

import br.com.maisprati.api.dto.ExerciseRequestDto;
import br.com.maisprati.api.dto.ExerciseResponseDto;
import br.com.maisprati.api.model.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    @Mapping(source = "pergunta", target = "pergunta")
    @Mapping(source = "alternativas", target = "alternativas")
    @Mapping(source = "respostaCorreta", target = "respostaCorreta")
    @Mapping(source = "listaId", target = "exerciseListId.id")
    @Mapping(source = "tipo", target = "tipo")
    Exercise toEntity(ExerciseRequestDto exerciseRequestDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "exerciseListId.id", target = "listaId")
    @Mapping(source = "pergunta", target = "pergunta")
    @Mapping(source = "alternativas", target = "alternativas")
    @Mapping(source = "respostaCorreta", target = "respostaCorreta")
    @Mapping(source = "tipo", target = "tipo")
    ExerciseResponseDto toResponse(Exercise exercise);
}