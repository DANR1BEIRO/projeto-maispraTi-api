package br.com.maisprati.api.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Classe centralizada para tratamento de exceções em toda a API.
 * Captura exceções lançadas pelos controllers e as converte em respostas HTTP adequadas.
 */
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * Trata exceções do tipo BadCredentialsException, que são lançadas
     * quando a autenticação falha (e-mail ou senha inválidos).
     *
     * @param e A exceção capturada.
     * @return uma resposta HTTP 401 Unauthorized com uma mensagem clara no corpo da resposta.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException e) {
        ErrorResponse erro = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Credenciais inválidas. Verifique seu e-mail e senha.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

    /**
     * Trata exceções genéricas do tipo RuntimeException.
     * Útil para capturar erros como o "E-mail já cadastrado!" do método de registro.
     *
     * @param e A exceção capturada.
     * @return uma resposta HTTP 400 Bad Request com a mensagem da exceção.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleGenericRuntimeException(RuntimeException e) {
        ErrorResponse erro = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    /**
     * DTO auxiliar privado para padronizar as respostas de erro da API.
     * Usar um 'record' aqui é uma forma moderna e concisa de criar um DTO imutável.
     */
    private record ErrorResponse(HttpStatus status, String message) {
        public int getStatusCode() {
            return status.value();
        }
    }
}
