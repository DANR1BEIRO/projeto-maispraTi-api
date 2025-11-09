package br.com.maisprati.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class JourneyProgressResponseDto {

    // DTO de resposta contendo o estado atual da jornada
// A nomenclatura foi atualizada de grupos para listas para refletir a nova regra do projeto

    private final String listaAtual;
    private final List<String> listasConcluidos;
    private final List<String> proximasListasBloqueadas;
}
