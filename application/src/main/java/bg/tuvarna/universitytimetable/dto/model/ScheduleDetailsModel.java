package bg.tuvarna.universitytimetable.dto.model;

import bg.tuvarna.universitytimetable.entity.enums.CourseWeek;
import bg.tuvarna.universitytimetable.entity.enums.SubjectType;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class ScheduleDetailsModel {

    private Long id;

    private DayOfWeek day;

    private LocalTime startTime;

    private LocalTime endTime;

    private String hexColor;

    private SubjectType subjectType;

    private String subjectNameBg;

    private String subjectNameEn;

    private String teacherNameBg;

    private String teacherNameEn;

    private String roomNumberBg;

    private String roomNumberEn;

    private CourseWeek week;

    private GroupScheduleModel group;
}
