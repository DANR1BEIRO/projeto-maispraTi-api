package br.com.maisprati.api.dto;

import lombok.Data;

@Data
public class RegisterDto {

    private String nomeCompleto;
    private String email;
    private String senha;
}
