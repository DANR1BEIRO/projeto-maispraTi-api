package br.com.maisprati.api.dto;

/**
 * DTO para encapsular a resposta de um login bem sucedido.
 * A única responsabilidade desta classe é carregar o token de acesso (JWT).
 *
 * @param token O token gerado que será enviado ao cliente.
 */

public record LoginResponseDto(String token) {
}
