package br.com.maisprati.api.controller;

import br.com.maisprati.api.dto.ExerciseGroupRequestDto;
import br.com.maisprati.api.dto.ExerciseGroupResponseDto;
import br.com.maisprati.api.service.ExerciseGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class ExerciseGroupController {

    private final ExerciseGroupService exerciseGroupService;

    @PostMapping("/exercise/group")
    public ResponseEntity<ExerciseGroupResponseDto> criarGrupoExercicios(@Valid @RequestBody ExerciseGroupRequestDto exerciseGroupRequestDto) {
        ExerciseGroupResponseDto exerciseGroupResponseDto = exerciseGroupService.criarGrupoExercicios(exerciseGroupRequestDto);
        return ResponseEntity.status(201).body(exerciseGroupResponseDto);
    }
}
