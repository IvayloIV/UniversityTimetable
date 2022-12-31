package bg.tuvarna.universitytimetable.repository.specification;

import bg.tuvarna.universitytimetable.entity.Schedule;
import bg.tuvarna.universitytimetable.entity.enums.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class ScheduleSpecification {

    public static Specification<Schedule> withStatuses(List<ScheduleStatus> status) {
        if (ObjectUtils.isEmpty(status)) {
            return null;
        } else {
            return (schedule, cq, cb) -> schedule.get("status").in(List.of(status));
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

    public static Specification<Schedule> withAcademicYear(Short year) {
        if (ObjectUtils.isEmpty(year)) {
            return null;
        } else {
            return (schedule, cq, cb) -> cb.equal(schedule.get("academicYear").get("year"), year);
        }
    }

    public static Specification<Schedule> withSemester(Semester semester) {
        if (ObjectUtils.isEmpty(semester)) {
            return null;
        } else {
            return (schedule, cq, cb) -> cb.equal(schedule.get("academicYear").get("semester"), semester);
        }
    }

    public static Specification<Schedule> withDegree(Degree degree) {
        if (ObjectUtils.isEmpty(degree)) {
            return null;
        } else {
            return (schedule, cq, cb) -> cb.equal(schedule.get("course").get("degree"), degree);
        }
    }

    public static Specification<Schedule> withSpecialtyId(Long specialtyId) {
        if (ObjectUtils.isEmpty(specialtyId)) {
            return null;
        } else {
            return (schedule, cq, cb) -> cb.equal(schedule.get("course").get("specialty").get("id"), specialtyId);
        }
    }

    public static Specification<Schedule> withCourseYear(CourseYear courseYear) {
        if (ObjectUtils.isEmpty(courseYear)) {
            return null;
        } else {
            return (schedule, cq, cb) -> cb.equal(schedule.get("course").get("year"), courseYear);
        }
    }

    public static Specification<Schedule> withCourseMode(CourseMode mode) {
        if (ObjectUtils.isEmpty(mode)) {
            return null;
        } else {
            return (schedule, cq, cb) -> cb.equal(schedule.get("course").get("mode"), mode);
        }
    }

    public static Specification<Schedule> withTeacherId(Long teacherId) {
        if (ObjectUtils.isEmpty(teacherId)) {
            return null;
        } else {
            return (schedule, cq, cb) -> cb.equal(schedule.get("course").get("teacher").get("id"), teacherId);
        }
    }
}
