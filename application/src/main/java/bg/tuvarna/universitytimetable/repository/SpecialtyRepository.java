package bg.tuvarna.universitytimetable.repository;

import bg.tuvarna.universitytimetable.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

    @Query("select s from Specialty s " +
            "where s.archived = false " +
            " and s.department.id = :departmentId " +
            "order by s.createdDate")
    List<Specialty> findByDepartmentId(@Param("departmentId") Long departmentId);

    Optional<Specialty> findByIdAndArchivedFalse(Long id);
}
