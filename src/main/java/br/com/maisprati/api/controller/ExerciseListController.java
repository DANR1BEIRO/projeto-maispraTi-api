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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//    @PreAuthorize("hasAnyRole('PROFESSOR')")
//    @PostMapping("/exercise")
//    public ResponseEntity<ExerciseListResponseDto> criarListaExercicio(@Valid @RequestBody ExerciseListRequestDto exerciseListRequestDto) {
//        ExerciseResponseDto exerciseListResponseDto = exerciseListService.criarListaExercicios(exerciseListRequestDto);
//        return ResponseEntity.status(201).body(exerciseListResponseDto);
//    }

}
