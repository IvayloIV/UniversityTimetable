package bg.tuvarna.universitytimetable.dto.model;

import bg.tuvarna.universitytimetable.entity.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.SortedSet;

@Data
@EqualsAndHashCode(exclude = {"groups"})
public class TeacherCourseModel {

    private Degree degree;

    private String specialtyNameBg;

    private CourseYear courseYear;

    private CourseMode mode;

    private CourseWeek week;

    private Short startWeek;

    private Short endWeek;

    private SortedSet<String> groups;
}
