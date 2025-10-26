package br.com.maisprati.api.controller;

import br.com.maisprati.api.dto.ExerciseListRequestDto;
import br.com.maisprati.api.dto.ExerciseListResponseDto;
import br.com.maisprati.api.dto.ExerciseRequestDto;
import br.com.maisprati.api.dto.ExerciseResponseDto;
import br.com.maisprati.api.model.ExerciseList;
import br.com.maisprati.api.service.ExerciseListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ExerciseListController {

    private final ExerciseListService exerciseListService;
    @PostMapping("/exercise/list")
    public ResponseEntity<ExerciseListResponseDto> criarListaExercicio(
            @Valid @RequestBody ExerciseListRequestDto exerciseListRequestDto) {

        ExerciseListResponseDto exerciseListResponseDto = exerciseListService.criarListaExercicios(exerciseListRequestDto);
        return ResponseEntity.status(201).body(exerciseListResponseDto);
    }

    @GetMapping("/exercise/list")
    public ResponseEntity<List<ExerciseListResponseDto>> buscarTodasListasExercicios(){
        List<ExerciseListResponseDto> exerciseListResponseDto = exerciseListService.buscarTodasListasExercicios();
        return ResponseEntity.status(200).body(exerciseListResponseDto);
    }

}
