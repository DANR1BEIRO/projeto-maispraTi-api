//package br.com.maisprati.api.repository;
//
//import br.com.maisprati.api.enuns.ProgressStatusEnum;
//import br.com.maisprati.api.model.UserProgress;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface UserProgressRepository extends JpaRepository<UserProgress, Integer> {
//
//    List<UserProgress> findByUser_Id(Integer userId);
//    Optional<UserProgress> findFirstByUser_IdAndStatusOrderByExercise_OrdemAsc(Integer userId, ProgressStatusEnum status);
//    Optional<UserProgress> findByUser_IdAndExercise_Id(Integer userId, Integer exerciseId);
//
//}
