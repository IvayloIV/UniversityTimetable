package bg.tuvarna.universitytimetable.repository.specification;

import bg.tuvarna.universitytimetable.entity.Schedule;
import bg.tuvarna.universitytimetable.entity.enums.ScheduleStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ScheduleSpecification {

    public static Specification<Schedule> withStatus(ScheduleStatus status) {
        if (ObjectUtils.isEmpty(status)) {
            return null;
        } else {
            return (schedule, cq, cb) -> cb.equal(schedule.get("status"), status);
        }
    }

    public static Specification<Schedule> withDay(DayOfWeek day) {
        if (ObjectUtils.isEmpty(day)) {
            return null;
        } else {
            return (schedule, cq, cb) -> cb.equal(schedule.get("day"), day);
        }
    }

    public static Specification<Schedule> withStartTimeBefore(LocalTime startTime) {
        if (ObjectUtils.isEmpty(startTime)) {
            return null;
        } else {
            return (schedule, cq, cb) -> cb.greaterThan(schedule.get("endTime"), startTime);
        }
    }

    public static Specification<Schedule> withEndTimeAfter(LocalTime endTime) {
        if (ObjectUtils.isEmpty(endTime)) {
            return null;
        } else {
            return (schedule, cq, cb) -> cb.lessThan(schedule.get("startTime"), endTime);
        }
    }
}
