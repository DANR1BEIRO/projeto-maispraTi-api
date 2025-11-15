package br.com.maisprati.api.mapper;

import br.com.maisprati.api.dto.ExerciseListRequestDto;
import br.com.maisprati.api.dto.ExerciseListResponseDto;
import br.com.maisprati.api.model.ExerciseList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExerciseListMapper {

    @Mapping(source = "titulo", target = "titulo")
    @Mapping(source = "descricao", target = "descricao")
    ExerciseList toEntity(ExerciseListRequestDto dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "titulo", target = "titulo")
    @Mapping(source = "descricao", target = "descricao")
   @Mapping(
            target = "exerciciosIds",
            expression = "java(entity.getExercicios().stream().map(e -> e.getId()).toList())"
    )
    ExerciseListResponseDto toResponse(ExerciseList entity);

    List<ExerciseListResponseDto> toResponseList(List<ExerciseList> entities);
}
