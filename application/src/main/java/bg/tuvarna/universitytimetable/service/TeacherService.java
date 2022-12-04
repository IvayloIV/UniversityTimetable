package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.data.CreateTeacherData;
import bg.tuvarna.universitytimetable.dto.model.TeacherListData;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface TeacherService {

    boolean createTeacher(CreateTeacherData createTeacherData, BindingResult bindingResult);

    List<TeacherListData> getList();

    void delete(Long teacherId);
}
