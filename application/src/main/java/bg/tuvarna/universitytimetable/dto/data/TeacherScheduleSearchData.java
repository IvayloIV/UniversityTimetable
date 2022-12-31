package bg.tuvarna.universitytimetable.dto.data;

import bg.tuvarna.universitytimetable.entity.enums.Semester;
import lombok.Data;

@Data
public class TeacherScheduleSearchData {

    private String academicYear;

    private Semester semester;

    private Long teacherId;
}
