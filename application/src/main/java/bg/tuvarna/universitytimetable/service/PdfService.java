package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.model.CourseScheduleModel;
import org.springframework.core.io.Resource;

import java.time.DayOfWeek;
import java.util.List;

public interface PdfService {

    Resource generateSchedule(CourseScheduleModel courseScheduleModel, List<DayOfWeek> daysOfWeek);
}
