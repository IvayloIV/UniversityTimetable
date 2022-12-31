package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.data.TeacherScheduleSearchData;
import bg.tuvarna.universitytimetable.dto.model.CourseScheduleModel;
import bg.tuvarna.universitytimetable.dto.model.TeacherScheduleModel;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;

public interface PdfService {

    Resource generateStudentSchedule(CourseScheduleModel courseScheduleModel);

    Resource generateTeacherSchedule(TeacherScheduleSearchData searchData, Map<String, List<TeacherScheduleModel>> schedule);
}
