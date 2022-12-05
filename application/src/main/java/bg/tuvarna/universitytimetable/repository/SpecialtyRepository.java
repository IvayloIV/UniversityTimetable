package bg.tuvarna.universitytimetable.repository;

import bg.tuvarna.universitytimetable.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

    @Query("select s from Specialty s " +
            "where s.archived = false " +
            " and s.department.id = :departmentId " +
            " and s.department.faculty.id = :facultyId " +
            "order by s.createdDate")
    List<Specialty> findByFacultyAndDepartment(@Param("facultyId") Long facultyId, @Param("departmentId") Long departmentId);
}
