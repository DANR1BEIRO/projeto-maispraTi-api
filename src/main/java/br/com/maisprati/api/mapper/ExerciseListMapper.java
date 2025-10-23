package br.com.maisprati.api.mapper;

import br.com.maisprati.api.dto.ExerciseListRequestDto;
import br.com.maisprati.api.dto.ExerciseListResponseDto;
import br.com.maisprati.api.dto.ExerciseRequestDto;
import br.com.maisprati.api.model.ExerciseList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExerciseListMapper {

//    @Mapping(source = "grupos", target = "grupos")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "titulo", target = "titulo")
    ExerciseList toEntity(ExerciseListRequestDto exerciseListRequestDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "titulo", target = "titulo")
    @Mapping(source = "descricao", target = "descricao")
//    @Mapping(source = "grupos", target = "grupos")
    @Mapping(source = "createdAt", target = "dataCriacao")
    @Mapping(source = "updatedAt", target = "dataAtualizacao")
    ExerciseListResponseDto toResponse(ExerciseList exerciseList);

}
