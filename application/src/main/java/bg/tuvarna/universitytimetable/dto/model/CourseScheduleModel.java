package bg.tuvarna.universitytimetable.dto.model;

import bg.tuvarna.universitytimetable.entity.enums.CourseMode;
import bg.tuvarna.universitytimetable.entity.enums.CourseYear;
import bg.tuvarna.universitytimetable.entity.enums.Degree;
import bg.tuvarna.universitytimetable.entity.enums.Semester;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

@Data
@EqualsAndHashCode(exclude={"groups", "schedules"})
public class CourseScheduleModel {

    private String year;

    private Semester semester;

    private String departmentNameBg;

    private String departmentNameEn;

    private String specialtyNameBg;

    private String specialtyNameEn;

    private Degree degree;

    private CourseYear courseYear;

    private CourseMode mode;

    private SortedSet<GroupScheduleModel> groups;

    private Map<String, List<ScheduleDetailsModel>> schedules;
}
