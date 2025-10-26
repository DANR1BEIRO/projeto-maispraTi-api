package br.com.maisprati.api.mapper;

import br.com.maisprati.api.dto.ExerciseGroupRequestDto;
import br.com.maisprati.api.dto.ExerciseGroupResponseDto;
import br.com.maisprati.api.model.ExerciseGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

//@Mapper(componentModel = "spring")
//public interface ExerciseGroupMapper {
//
//    // DTO → Entity
//    @Mapping(source = "titulo", target = "title")
//    @Mapping(source = "ordem", target = "sequence")
//    @Mapping(source = "listaId", target = "listId")
//    ExerciseGroup toEntity(ExerciseGroupRequestDto exerciseGroupRequestDto);
//
//    // Entity → Response DTO
//    @Mapping(source = "title", target = "titulo")
//    @Mapping(source = "sequence", target = "ordem")
//    @Mapping(source = "listId", target = "listaId")
//    ExerciseGroupResponseDto toResponse(ExerciseGroup exerciseGroup);
//
//    @Mapping(source = "title", target = "titulo")
//    @Mapping(source = "sequence", target = "ordem")
//    @Mapping(source = "listId", target = "listaId")
//    List<ExerciseGroupResponseDto> toResponseList(List<ExerciseGroup> exerciseGroup);
//
//}

@Mapper(componentModel = "spring")
public interface ExerciseGroupMapper {

    // De entidade para DTO (resposta)
    @Mapping(source = "title", target = "titulo")
    @Mapping(source = "sequence", target = "ordem")
    @Mapping(source = "exerciseList.id", target = "listaId")
    ExerciseGroupResponseDto toResponse(ExerciseGroup entity);

    List<ExerciseGroupResponseDto> toResponseList(List<ExerciseGroup> entities);

    // De DTO (request) para entidade
    @Mapping(source = "titulo", target = "title")
    @Mapping(source = "ordem", target = "sequence")
    @Mapping(target = "exerciseList.id", source = "listaId")
    ExerciseGroup toEntity(ExerciseGroupRequestDto dto);

    List<ExerciseGroup> toEntityList(List<ExerciseGroupRequestDto> dtos);
}


