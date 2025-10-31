package br.com.maisprati.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class JourneyProgressResponseDto {

    private final String grupoAtual;
    private final List<String> gruposConcluidos;
    private final List<String> proximosGruposBloqueados;
}
