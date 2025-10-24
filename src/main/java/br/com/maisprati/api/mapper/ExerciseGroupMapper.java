package br.com.maisprati.api.mapper;

import br.com.maisprati.api.dto.ExerciseGroupRequestDto;
import br.com.maisprati.api.dto.ExerciseGroupResponseDto;
import br.com.maisprati.api.model.ExerciseGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExerciseGroupMapper {

    // DTO → Entity
    @Mapping(source = "titulo", target = "title")
    @Mapping(source = "ordem", target = "sequence")
    @Mapping(source = "listaId", target = "listId")
    ExerciseGroup toEntity(ExerciseGroupRequestDto exerciseGroupRequestDto);

    // Entity → Response DTO
    @Mapping(source = "title", target = "titulo")
    @Mapping(source = "sequence", target = "ordem")
    @Mapping(source = "listId", target = "listaId")
    ExerciseGroupResponseDto toResponse(ExerciseGroup exerciseGroup);
}

