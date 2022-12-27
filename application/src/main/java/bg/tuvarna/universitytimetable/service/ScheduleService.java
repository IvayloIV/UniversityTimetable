package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.data.ScheduleEditData;
import bg.tuvarna.universitytimetable.dto.model.FacultyScheduleModel;
import bg.tuvarna.universitytimetable.dto.model.ScheduleEditModel;

import java.util.List;

public interface ScheduleService {

    List<FacultyScheduleModel> findAll();

    void save();

    ScheduleEditModel getEditModel(Long id);

    void edit(Long id, ScheduleEditData scheduleEditData);
}
