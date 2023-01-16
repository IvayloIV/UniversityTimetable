package bg.tuvarna.universitytimetable.dto.model;

import bg.tuvarna.universitytimetable.entity.enums.CourseMode;
import bg.tuvarna.universitytimetable.entity.enums.CourseYear;
import bg.tuvarna.universitytimetable.entity.enums.Degree;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleGenerationModel {

    private Long specialtyId;

    private Degree degree;

    private CourseYear year;

    private CourseMode mode;
}
