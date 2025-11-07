package br.com.maisprati.api.controller;

import br.com.maisprati.api.dto.UserAnswerRequestDto;
import br.com.maisprati.api.dto.UserAnswerResponseDto;
import br.com.maisprati.api.service.UserAnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class UserAnswerController {
    private final UserAnswerService userAnswerService;

    @PostMapping("/submit")
    public ResponseEntity<UserAnswerResponseDto> enviarResposta(@Valid @RequestBody UserAnswerRequestDto userAnswerRequestDto){
        UserAnswerResponseDto userAnswerResponseDto = userAnswerService.criarResposta(userAnswerRequestDto);
        return ResponseEntity.status(201).body(userAnswerResponseDto);
    }
}
