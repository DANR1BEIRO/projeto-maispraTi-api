package br.com.maisprati.api.controller;

import br.com.maisprati.api.dto.ExerciseDto;
import br.com.maisprati.api.dto.ExerciseResponseDto;
import br.com.maisprati.api.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping("/exercise")
    public ResponseEntity<ExerciseResponseDto> criarExercicio(@RequestBody ExerciseDto exerciseDto) {
        ExerciseResponseDto exerciseResponseDto = exerciseService.criarExercicio(exerciseDto);
        return ResponseEntity.status(201).body(exerciseResponseDto);
    }

    @PutMapping("/exercise/{id}")
    public ResponseEntity<ExerciseResponseDto> atualizarExercicio(@PathVariable Integer id, @RequestBody ExerciseDto exerciseDto) {
        ExerciseResponseDto exerciseResponseDto = exerciseService.editarExercicio(id, exerciseDto);
        return ResponseEntity.status(201).body(exerciseResponseDto);
    }

    @DeleteMapping("/exercise/{id}")
    public ResponseEntity<ExerciseResponseDto> deletarExercicio(@PathVariable Integer id){
        ExerciseResponseDto exerciseResponseDto = exerciseService.excluirExercicio(id);
        return ResponseEntity.status(201).body(exerciseResponseDto);
    }

    @GetMapping("/exercise/{id}")
    public ResponseEntity<ExerciseResponseDto> buscarExercicio(@PathVariable Integer id){
        ExerciseResponseDto exerciseResponseDto = exerciseService.buscarExercicio(id);
        return ResponseEntity.status(201).body(exerciseResponseDto);
    }
}
