package br.com.maisprati.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JourneyAdvanceRequestDto {
    @NotBlank(message = "O nome do grupo é obrigatório")
    private String grupoTentandoConcluir;
}
