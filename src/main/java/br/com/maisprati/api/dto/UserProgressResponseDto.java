package br.com.maisprati.api.dto;

import br.com.maisprati.api.enuns.ProgressStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserProgressResponseDto {
    private Integer exerciseId;
    private String pergunta;
    private String tipo;
    private String ordem;
    private ProgressStatusEnum status;
    private Boolean respondidoCorretamente;
}
