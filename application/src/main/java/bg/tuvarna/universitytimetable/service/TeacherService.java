package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.data.CreateTeacherData;
import bg.tuvarna.universitytimetable.dto.model.TeacherListModel;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface TeacherService {

    boolean createTeacher(CreateTeacherData createTeacherData, BindingResult bindingResult);

    List<TeacherListModel> getList();

    void delete(Long teacherId);
}
