package bg.tuvarna.universitytimetable.repository;

import bg.tuvarna.universitytimetable.entity.Schedule;
import bg.tuvarna.universitytimetable.entity.enums.ScheduleStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByStatus(ScheduleStatus status, Sort sort);
}
