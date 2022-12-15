package bg.tuvarna.universitytimetable.repository;

import bg.tuvarna.universitytimetable.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    List<Teacher> findAllByArchivedFalse();

    Boolean existsByUcnAndArchivedFalse(String ucn);

    Optional<Teacher> findByIdAndArchivedFalse(Long id);
}
