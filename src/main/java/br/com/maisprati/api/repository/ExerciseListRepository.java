package br.com.maisprati.api.repository;

import br.com.maisprati.api.model.ExerciseList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExerciseListRepository extends JpaRepository<ExerciseList, Integer> {

    Optional<ExerciseList> findByTitulo(String titulo);

    @Query("SELECT el FROM ExerciseList el WHERE el.ordem = :ordem + 1")
    Optional<ExerciseList> buscarProximaLista(@Param("ordem") Integer ordem);

//    List<ExerciseList> findAllByIdByIdAsc();
//    Optional<ExerciseList> findByOrdem(Integer ordem);
}
