package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.data.CreateTeacherData;
import bg.tuvarna.universitytimetable.dto.model.TeacherListModel;
import bg.tuvarna.universitytimetable.dto.model.TeacherScheduleFilterModel;
import bg.tuvarna.universitytimetable.entity.Teacher;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface TeacherService {

    boolean createTeacher(CreateTeacherData createTeacherData, BindingResult bindingResult);

    List<TeacherListModel> getList();

    void delete(Long teacherId);

    TeacherScheduleFilterModel getFilterModelById(Long id);

    List<TeacherScheduleFilterModel> getFilterModels();

    Teacher getById(Long id);
}
