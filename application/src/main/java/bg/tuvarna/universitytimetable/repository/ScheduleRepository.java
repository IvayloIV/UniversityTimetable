package bg.tuvarna.universitytimetable.repository;

import bg.tuvarna.universitytimetable.entity.Schedule;
import bg.tuvarna.universitytimetable.entity.enums.ScheduleStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>, JpaSpecificationExecutor<Schedule> {

    long countByStatus(ScheduleStatus status);

    List<Schedule> findAllByStatus(ScheduleStatus status, Sort sort);

    @Modifying
    @Query("update Schedule s " +
            "set s.status = :newStatus " +
            "where s.status = :oldStatus")
    void updateStatus(@Param("oldStatus") ScheduleStatus oldStatus, @Param("newStatus") ScheduleStatus newStatus);
}
