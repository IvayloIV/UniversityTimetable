package bg.tuvarna.universitytimetable.dto.model;

import bg.tuvarna.universitytimetable.entity.enums.CourseMode;
import bg.tuvarna.universitytimetable.entity.enums.CourseWeek;
import bg.tuvarna.universitytimetable.entity.enums.CourseYear;
import bg.tuvarna.universitytimetable.entity.enums.Degree;
import lombok.Data;

import java.util.List;

@Data
public class CourseListModel {

    private Long id;

    private Degree degree;

    private String faculty;

    private String department;

    private String specialty;

    private CourseYear year;

    private CourseMode mode;

    private List<GroupListModel> groups;

    private String room;

    private String teacher;

    private CourseWeek week;

    private Short startWeek;

    private Short endWeek;

    private Short hoursPerWeek;

    private Short meetingsPerWeek;

    private Boolean active;

    private List<CourseTimeListModel> courseTimes;
}
