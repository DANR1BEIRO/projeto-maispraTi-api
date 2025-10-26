package br.com.maisprati.api.mapper;

import br.com.maisprati.api.dto.UserResponseDto;
import br.com.maisprati.api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nomeCompleto", target = "nome")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fotoPerfil", target = "fotoPerfil")
//    @Mapping(source = "grupoAtual", target = "grupoAtual")
    @Mapping(source = "streakAtual", target = "streakAtual")
    UserResponseDto toResponse(User user);
}
