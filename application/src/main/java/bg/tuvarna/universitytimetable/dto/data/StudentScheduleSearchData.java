package bg.tuvarna.universitytimetable.dto.data;

import bg.tuvarna.universitytimetable.entity.enums.CourseMode;
import bg.tuvarna.universitytimetable.entity.enums.CourseYear;
import bg.tuvarna.universitytimetable.entity.enums.Degree;
import bg.tuvarna.universitytimetable.entity.enums.Semester;
import lombok.Data;

@Data
public class StudentScheduleSearchData {

    private String academicYear;

    private Semester semester;

    private Degree degree;

    private Long specialtyId;

    private CourseYear courseYear;

    private CourseMode mode;
}
