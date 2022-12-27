package bg.tuvarna.universitytimetable.dto.model;

import bg.tuvarna.universitytimetable.entity.enums.CourseMode;
import bg.tuvarna.universitytimetable.entity.enums.CourseYear;
import bg.tuvarna.universitytimetable.entity.enums.Degree;
import bg.tuvarna.universitytimetable.entity.enums.SubjectType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
public class ScheduleEditModel {

    private Long id;

    private Degree degree;

    private String facultyName;

    private String departmentName;

    private String specialtyName;

    private CourseYear year;

    private CourseMode mode;

    private String groupName;

    private String subjectName;

    private SubjectType subjectType;

    private Integer day;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;
}
