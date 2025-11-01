package br.com.maisprati.api.controller;

import br.com.maisprati.api.dto.ExerciseGroupRequestDto;
import br.com.maisprati.api.dto.ExerciseGroupResponseDto;
import br.com.maisprati.api.service.ExerciseGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ExerciseGroupController {

    private final ExerciseGroupService exerciseGroupService;

    @PostMapping("/admin/exercise/group")
    public ResponseEntity<ExerciseGroupResponseDto> criarGrupoExercicios(@Valid @RequestBody ExerciseGroupRequestDto exerciseGroupRequestDto) {
        ExerciseGroupResponseDto exerciseGroupResponseDto = exerciseGroupService.criarGrupoExercicios(exerciseGroupRequestDto);
        return ResponseEntity.status(201).body(exerciseGroupResponseDto);
    }

    @GetMapping("/admin/exercise/group")
    public ResponseEntity<List<ExerciseGroupResponseDto>> buscarTodosGruposExercicios(){
        List<ExerciseGroupResponseDto> exerciseGroupResponseDto = exerciseGroupService.buscarTodosGruposExercicios();
        return ResponseEntity.status(200).body(exerciseGroupResponseDto);

    }

//    @GetMapping("/admin/exercise/group/{id}")
//    public ResponseEntity<ExerciseGroupResponseDto> buscarGrupoPorId(@Valid @PathVariable Integer id){
//        ExerciseGroupResponseDto exerciseGroupResponseDto = exerciseGroupService.buscarGrupoPorIdDTO(id);
//        return ResponseEntity.status(200).body(exerciseGroupResponseDto);
//    }

}
