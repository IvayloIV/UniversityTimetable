package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.model.FacultyScheduleModel;

import java.util.List;

public interface ScheduleService {

    List<FacultyScheduleModel> findAll();
}
