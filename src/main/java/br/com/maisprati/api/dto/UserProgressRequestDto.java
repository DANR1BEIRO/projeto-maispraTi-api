package br.com.maisprati.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserProgressRequestDto {
    private Integer userId;
    private Integer exerciseId;
}


