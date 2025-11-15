package br.com.maisprati.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class JourneyProgressResponseDto {

    private final String listaAtual;
    private final List<String> listasConcluidos;
    private final List<String> proximasListasBloqueadas;
}
