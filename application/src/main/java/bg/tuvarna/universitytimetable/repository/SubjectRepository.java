package bg.tuvarna.universitytimetable.repository;

import bg.tuvarna.universitytimetable.entity.Subject;
import bg.tuvarna.universitytimetable.entity.enums.SubjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    boolean existsByNameBgAndTypeAndArchivedFalse(String nameBg, SubjectType type);
}
