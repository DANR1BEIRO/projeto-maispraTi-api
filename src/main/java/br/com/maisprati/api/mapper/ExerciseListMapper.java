package br.com.maisprati.api.mapper;

import br.com.maisprati.api.dto.ExerciseListRequestDto;
import br.com.maisprati.api.dto.ExerciseListResponseDto;
import br.com.maisprati.api.dto.ExerciseRequestDto;
import br.com.maisprati.api.model.ExerciseList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

//@Mapper(componentModel = "spring")
//public interface ExerciseListMapper {
//
//    @Mapping(source = "grupos", target = "grupos")
//    @Mapping(source = "descricao", target = "descricao")
//    @Mapping(source = "titulo", target = "titulo")
//    ExerciseList toEntity(ExerciseListRequestDto exerciseListRequestDto);
//
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "titulo", target = "titulo")
//    @Mapping(source = "descricao", target = "descricao")
//    @Mapping(source = "grupos", target = "grupos")
//    @Mapping(source = "createdAt", target = "dataCriacao")
//    @Mapping(source = "updatedAt", target = "dataAtualizacao")
//    ExerciseListResponseDto toResponse(ExerciseList exerciseList);
//
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "titulo", target = "titulo")
//    @Mapping(source = "descricao", target = "descricao")
//    @Mapping(source = "grupos", target = "grupos")
//    @Mapping(source = "createdAt", target = "dataCriacao")
//    @Mapping(source = "updatedAt", target = "dataAtualizacao")
//    List<ExerciseListResponseDto> toResponseList(List<ExerciseList> exerciseList);
//
//    List<ExerciseListResponseDto> toResponseList(List<ExerciseList> entities);
//}

@Mapper(componentModel = "spring", uses = {ExerciseGroupMapper.class})
public interface ExerciseListMapper {

    // Request → Entity
    @Mapping(source = "titulo", target = "titulo")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "grupos", target = "grupos")
//    @Mapping(source = "exercicios", target = "exercicios")
    ExerciseList toEntity(ExerciseListRequestDto dto);

    // Entity → Response
    @Mapping(source = "id", target = "id")
    @Mapping(source = "titulo", target = "titulo")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "grupos", target = "grupos") // usa o ExerciseGroupMapper
//    @Mapping(source = "createdAt", target = "dataCriacao")
//    @Mapping(source = "updatedAt", target = "dataAtualizacao")
    @Mapping(source = "exercicios", target = "exercicios")
    ExerciseListResponseDto toResponse(ExerciseList entity);

    List<ExerciseListResponseDto> toResponseList(List<ExerciseList> entities);
}
