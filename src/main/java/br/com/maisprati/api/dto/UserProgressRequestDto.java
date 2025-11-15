package br.com.maisprati.api.dto;

import br.com.maisprati.api.enuns.ProgressStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserProgressRequestDto {
    private Integer userId;
    private Integer exerciseId;
}


