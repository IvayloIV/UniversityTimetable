package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.data.ScheduleEditData;
import bg.tuvarna.universitytimetable.dto.data.StudentScheduleSearchData;
import bg.tuvarna.universitytimetable.dto.model.FacultyScheduleModel;
import bg.tuvarna.universitytimetable.dto.model.ScheduleEditModel;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface ScheduleService {

    List<FacultyScheduleModel> findAll();

    void save();

    ScheduleEditModel getEditModel(Long id);

    void edit(Long id, ScheduleEditData scheduleEditData);

    void loadStudentSchedule(StudentScheduleSearchData studentScheduleSearchData, ModelAndView modelAndView);
}
