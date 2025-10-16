package br.com.maisprati.api.controller;

import br.com.maisprati.api.dto.ExerciseRequestDto;
import br.com.maisprati.api.dto.ExerciseResponseDto;
import br.com.maisprati.api.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PostMapping("/exercise")
    public ResponseEntity<ExerciseResponseDto> criarExercicio(@RequestBody ExerciseRequestDto exerciseRequestDto) {
        ExerciseResponseDto exerciseResponseDto = exerciseService.criarExercicio(exerciseRequestDto);
        return ResponseEntity.status(201).body(exerciseResponseDto);
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @PutMapping("/exercise/{id}")
    public ResponseEntity<ExerciseResponseDto> atualizarExercicio(@PathVariable Integer id, @RequestBody ExerciseRequestDto exerciseRequestDto) {
        ExerciseResponseDto exerciseResponseDto = exerciseService.editarExercicio(id, exerciseRequestDto);
        return ResponseEntity.status(201).body(exerciseResponseDto);
    }

    @PreAuthorize("hasAnyRole('PROFESSOR')")
    @DeleteMapping("/exercise/{id}")
    public ResponseEntity<ExerciseResponseDto> deletarExercicio(@PathVariable Integer id){
        ExerciseResponseDto exerciseResponseDto = exerciseService.excluirExercicio(id);
        return ResponseEntity.status(201).body(exerciseResponseDto);
    }

    @PreAuthorize("hasAnyRole('ALUNO', 'PROFESSOR')")
    @GetMapping("/exercise/{id}")
    public ResponseEntity<ExerciseResponseDto> buscarExercicio(@PathVariable Integer id){
        ExerciseResponseDto exerciseResponseDto = exerciseService.buscarExercicio(id);
        return ResponseEntity.status(201).body(exerciseResponseDto);
    }
}
