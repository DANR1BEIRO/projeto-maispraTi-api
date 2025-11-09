package br.com.maisprati.api.controller;

import br.com.maisprati.api.dto.UserAnswerRequestDto;
import br.com.maisprati.api.dto.UserAnswerResponseDto;
import br.com.maisprati.api.service.UserAnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/submit/{id}")
    public ResponseEntity<UserAnswerResponseDto> buscarResposta(@PathVariable Integer id){
        UserAnswerResponseDto userAnswerResponseDto = userAnswerService.buscarResposta(id);
        return ResponseEntity.status(200).body(userAnswerResponseDto);
    }
}
