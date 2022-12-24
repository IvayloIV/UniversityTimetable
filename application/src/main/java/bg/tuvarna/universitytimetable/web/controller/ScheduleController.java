
package bg.tuvarna.universitytimetable.web.controller;

import bg.tuvarna.universitytimetable.dto.model.*;
import bg.tuvarna.universitytimetable.entity.enums.*;
import bg.tuvarna.universitytimetable.service.FacultyService;
import bg.tuvarna.universitytimetable.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/schedule")
public class ScheduleController extends BaseController {

    private final SpecialtyService specialtyService;
    private final FacultyService facultyService;

    @Autowired
    public ScheduleController(SpecialtyService specialtyService,
                              FacultyService facultyService) {
        this.specialtyService = specialtyService;
        this.facultyService = facultyService;
    }

    @ModelAttribute("daysOfWeek")
    public List<DayOfWeek> daysOfWeek() {
        return List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
    }

    @GetMapping("/generate")
    public ModelAndView getGeneratedSchedules(ModelAndView modelAndView) {
        ScheduleDetailsModel schedule1 = new ScheduleDetailsModel();
        schedule1.setDay(DayOfWeek.MONDAY);
        schedule1.setStartTime(LocalTime.of(8, 45));
        schedule1.setEndTime(LocalTime.of(11, 0));
        schedule1.setHexColor("#E1E2ED");
        schedule1.setSubjectType(SubjectType.COURSEWORK);
        schedule1.setSubjectNameBg("УИС");
        schedule1.setSubjectNameEn("UIS");
        schedule1.setTeacherNameBg("доц. д-р Диян Динев");
        schedule1.setTeacherNameEn("prod. d-r Diqn Dinev");
        schedule1.setRoomNumberBg("1354 НУК");
        schedule1.setRoomNumberEn("1354 NUK");
        schedule1.setWeek(CourseWeek.ALL);

        ScheduleDetailsModel schedule2 = new ScheduleDetailsModel();
        schedule2.setDay(DayOfWeek.MONDAY);
        schedule2.setStartTime(LocalTime.of(11, 0));
        schedule2.setEndTime(LocalTime.of(12, 45));
        schedule2.setHexColor("#F7DBA7");
        schedule2.setSubjectType(SubjectType.LECTURE);
        schedule2.setSubjectNameBg("ППC#");
        schedule2.setSubjectNameEn("PPC#");
        schedule2.setTeacherNameBg("доц. д-р Виолета Божикова");
        schedule2.setTeacherNameEn("prod. d-r Violeta Bojikowa");
        schedule2.setRoomNumberBg("536 НУК");
        schedule2.setRoomNumberEn("536 NUK");
        schedule2.setWeek(CourseWeek.EVEN);

        ScheduleDetailsModel schedule3 = new ScheduleDetailsModel();
        schedule3.setDay(DayOfWeek.FRIDAY);
        schedule3.setStartTime(LocalTime.of(8, 45));
        schedule3.setEndTime(LocalTime.of(11, 0));
        schedule3.setHexColor("#F7DBA8");
        schedule3.setSubjectType(SubjectType.LECTURE);
        schedule3.setSubjectNameBg("ППC#");
        schedule3.setSubjectNameEn("PPC#");
        schedule3.setTeacherNameBg("доц. д-р Виолета Божикова");
        schedule3.setTeacherNameEn("prod. d-r Violeta Bojikowa");
        schedule3.setRoomNumberBg("536 НУК");
        schedule3.setRoomNumberEn("536 NUK");
        schedule3.setWeek(CourseWeek.EVEN);

        ScheduleDetailsModel schedule4 = new ScheduleDetailsModel();
        schedule4.setDay(DayOfWeek.MONDAY);
        schedule4.setStartTime(LocalTime.of(8, 45));
        schedule4.setEndTime(LocalTime.of(11, 0));
        schedule4.setHexColor("#E1E2E2");
        schedule4.setSubjectType(SubjectType.COURSEWORK);
        schedule4.setSubjectNameBg("УИС");
        schedule4.setSubjectNameEn("UIS");
        schedule4.setTeacherNameBg("доц. д-р Диян Динев");
        schedule4.setTeacherNameEn("prod. d-r Diqn Dinev");
        schedule4.setRoomNumberBg("1354 НУК");
        schedule4.setRoomNumberEn("1354 NUK");
        schedule4.setWeek(CourseWeek.ALL);

        GroupScheduleModel group1 = new GroupScheduleModel();
        group1.setId(1L);
        group1.setName("I гр.");

        GroupScheduleModel group2 = new GroupScheduleModel();
        group2.setId(2L);
        group2.setName("II гр.");

        schedule1.setGroup(group1);
//        schedule3.setGroup(group1);
        schedule4.setGroup(group2);

        CourseScheduleModel courseScheduleModel = new CourseScheduleModel();
        courseScheduleModel.setYear("2022/2023");
        courseScheduleModel.setSemester(Semester.WINTER);
        courseScheduleModel.setDepartmentNameBg("СИТ");
        courseScheduleModel.setDepartmentNameEn("SIT");
        courseScheduleModel.setSpecialtyNameBg("СИ");
        courseScheduleModel.setSpecialtyNameEn("SI");
        courseScheduleModel.setDegree(Degree.MASTER_BG_1_5);
        courseScheduleModel.setCourseYear(CourseYear.I);
        courseScheduleModel.setMode(CourseMode.FULL_TIME);
        courseScheduleModel.setGroups(List.of(group1, group2));
        courseScheduleModel.setSchedules(
                Map.of(LocalTime.of(8, 45).format(DateTimeFormatter.ofPattern("H:m")), List.of(schedule1, schedule4, schedule3),
                        LocalTime.of(11, 0).format(DateTimeFormatter.ofPattern("H:m")), List.of(schedule2)));

        FacultyScheduleModel faculty = new FacultyScheduleModel();
        faculty.setNameBg("ФИТА");
        faculty.setCourses(List.of(courseScheduleModel));

        modelAndView.addObject("faculties", List.of(faculty));
        return view("schedule/list", modelAndView);
    }
}
