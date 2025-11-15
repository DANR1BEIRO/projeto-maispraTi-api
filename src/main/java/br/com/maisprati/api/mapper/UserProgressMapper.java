//package br.com.maisprati.api.mapper;
//
//import br.com.maisprati.api.dto.UserProgressResponseDto;
//import br.com.maisprati.api.model.UserProgress;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper(componentModel = "spring")
//public interface UserProgressMapper {
//    UserProgressMapper INSTANCE = Mappers.getMapper(UserProgressMapper.class);
//
//    @Mapping(source = "exercise.id", target = "exerciseId")
//    @Mapping(source = "exercise.pergunta", target = "pergunta")
//    @Mapping(source = "exercise.tipo", target = "tipo")
////    @Mapping(source = "exercise.ordem", target = "ordem")
//    UserProgressResponseDto toResponse(UserProgress userProgress);
//
//}
