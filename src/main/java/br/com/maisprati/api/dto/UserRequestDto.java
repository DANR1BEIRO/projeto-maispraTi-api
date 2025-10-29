package br.com.maisprati.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDto {
    private String nomeCompleto;
    private String fotoPerfil;
}

