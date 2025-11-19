package br.com.maisprati.api.controller;

import br.com.maisprati.api.dto.ExercisePutRequestDto;
import br.com.maisprati.api.dto.ExerciseRequestDto;
import br.com.maisprati.api.dto.ExerciseResponseDto;
import br.com.maisprati.api.service.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping("/admin/exercise")
    public ResponseEntity<ExerciseResponseDto> criarExercicio(@Valid @RequestBody ExerciseRequestDto exerciseRequestDto) {
        ExerciseResponseDto exerciseResponseDto = exerciseService.criarExercicio(exerciseRequestDto);
        return ResponseEntity.status(201).body(exerciseResponseDto);
    }

    @PutMapping("/admin/exercise/{id}")
    public ResponseEntity<ExerciseResponseDto> atualizarExercicio(@PathVariable Integer id, @RequestBody ExercisePutRequestDto exercisePutRequestDto) {
        ExerciseResponseDto exerciseResponseDto = exerciseService.editarExercicio(id, exercisePutRequestDto);
        return ResponseEntity.status(201).body(exerciseResponseDto);
    }

    @DeleteMapping("/admin/exercise/{id}")
    public ResponseEntity<ExerciseResponseDto> deletarExercicio(@PathVariable Integer id){
        ExerciseResponseDto exerciseResponseDto = exerciseService.excluirExercicio(id);
        return ResponseEntity.status(201).body(exerciseResponseDto);
    }

    @GetMapping("/admin/exercise/{id}")
    public ResponseEntity<ExerciseResponseDto> buscarExercicio(@PathVariable Integer id){
        ExerciseResponseDto exerciseResponseDto = exerciseService.buscarExercicio(id);
        return ResponseEntity.status(200).body(exerciseResponseDto);
    }
}
