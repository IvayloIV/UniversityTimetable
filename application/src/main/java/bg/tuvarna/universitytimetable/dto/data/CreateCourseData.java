package bg.tuvarna.universitytimetable.dto.data;

import bg.tuvarna.universitytimetable.entity.enums.CourseMode;
import bg.tuvarna.universitytimetable.entity.enums.CourseWeek;
import bg.tuvarna.universitytimetable.entity.enums.CourseYear;
import bg.tuvarna.universitytimetable.entity.enums.Degree;
import lombok.Data;

import java.util.List;

@Data
public class CreateCourseData {

    private Degree degree;
    private CourseMode mode;
    private Long facultyId;
    private CourseWeek week;
    private Long departmentId;
    private Short startWeek;
    private Long specialtyId;
    private Short endWeek;
    private CourseYear year;
    private Short hoursPerWeek;
    private Long roomId;
    private Short meetingsPerWeek;
    private Long teacherId;
    private List<String> groups;
}
