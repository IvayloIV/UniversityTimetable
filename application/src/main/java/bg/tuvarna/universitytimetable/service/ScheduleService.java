package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.data.ScheduleEditData;
import bg.tuvarna.universitytimetable.dto.data.StudentScheduleSearchData;
import bg.tuvarna.universitytimetable.dto.data.TeacherScheduleSearchData;
import bg.tuvarna.universitytimetable.dto.model.FacultyScheduleModel;
import bg.tuvarna.universitytimetable.dto.model.ScheduleEditModel;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

public interface ScheduleService {

    List<FacultyScheduleModel> findAll();

    void save();

    ScheduleEditModel getEditModel(Long id);

    void edit(Long id, ScheduleEditData scheduleEditData);

    void loadStudentSchedule(StudentScheduleSearchData studentScheduleSearchData, ModelAndView modelAndView);
    ResponseEntity<Resource> generateStudentSchedule(StudentScheduleSearchData studentScheduleSearchData);

    void notifyStudents(MultipartFile studentsNameCsv, StudentScheduleSearchData searchData, RedirectAttributes attributes) throws IOException;
    void loadTeacherSchedule(TeacherScheduleSearchData teacherScheduleSearchData, ModelAndView modelAndView);

    ResponseEntity<Resource> generateTeacherSchedule(TeacherScheduleSearchData teacherScheduleSearchData);

    void notifyTeacher(TeacherScheduleSearchData teacherScheduleSearchData, RedirectAttributes attributes) throws IOException;
}
