package br.com.maisprati.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

// DTO enviado pelo cliente para avançar a jornada.
// Antes usava grupoTentandoConcluir, mas agora a jornada funciona por listas.
// Por isso foi renomeado para listaTentandoConcluir.

@Getter
@NoArgsConstructor
public class JourneyAdvanceRequestDto {

    /**
     * Nome da lista que o usuário está tentando concluir
     */
    private String listaTentandoConcluir;
}
