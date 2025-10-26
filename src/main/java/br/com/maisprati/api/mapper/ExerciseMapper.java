package br.com.maisprati.api.mapper;

import br.com.maisprati.api.dto.ExerciseRequestDto;
import br.com.maisprati.api.dto.ExerciseResponseDto;
import br.com.maisprati.api.model.Exercise;
import br.com.maisprati.api.model.ExerciseGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    @Mapping(source = "grupoId", target = "grupo")
    @Mapping(source = "pergunta", target = "pergunta")
    @Mapping(source = "alternativas", target = "alternativas")
    @Mapping(source = "respostaCorreta", target = "respostaCorreta")
    Exercise toEntity(ExerciseRequestDto exerciseRequestDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "grupo.id", target = "grupoId")
    @Mapping(source = "pergunta", target = "pergunta")
    @Mapping(source = "alternativas", target = "alternativas")
    @Mapping(source = "respostaCorreta", target = "respostaCorreta")
    ExerciseResponseDto toResponse(Exercise exercise);

    default ExerciseGroup map(Integer grupoId) {
        if (grupoId == null) return null;
        ExerciseGroup group = new ExerciseGroup();
        group.setId(grupoId);
        return group;
    }

}
