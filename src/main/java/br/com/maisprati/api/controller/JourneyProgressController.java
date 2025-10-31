package br.com.maisprati.api.controller;

import br.com.maisprati.api.dto.JourneyAdvanceRequestDto;
import br.com.maisprati.api.dto.JourneyProgressResponseDto;
import br.com.maisprati.api.service.JourneyProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/journey")
@RequiredArgsConstructor
public class JourneyProgressController {

    private final JourneyProgressService journeyProgressService;

    @GetMapping("/{userId}")
    public ResponseEntity<JourneyProgressResponseDto> getJourneyProgress(@PathVariable Integer userId) {
        JourneyProgressResponseDto responseDto = journeyProgressService.consultarProgresso(userId);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/advance/{userId}")
    public ResponseEntity<JourneyProgressResponseDto> avancarProgresso(@PathVariable Integer userId, @RequestBody JourneyAdvanceRequestDto dto) {
        JourneyProgressResponseDto response = journeyProgressService.avancarProgresso(userId, dto);
        return ResponseEntity.ok(response);
    }
}
