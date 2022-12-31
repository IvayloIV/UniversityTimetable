package bg.tuvarna.universitytimetable.dto.model;

import bg.tuvarna.universitytimetable.entity.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"hexColor", "week", "courses"})
public class TeacherScheduleModel {

    private DayOfWeek day;

    private LocalTime startTime;

    private LocalTime endTime;

    private String hexColor;

    private SubjectType subjectType;

    private String subjectNameBg;

    private String roomNumberBg;

    private CourseWeek week;

    private Set<TeacherCourseModel> courses;
}
