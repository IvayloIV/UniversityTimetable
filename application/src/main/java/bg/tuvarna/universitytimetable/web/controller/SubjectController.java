package bg.tuvarna.universitytimetable.web.controller;

import bg.tuvarna.universitytimetable.dto.data.CreateSubjectData;
import bg.tuvarna.universitytimetable.dto.model.FacultyListModel;
import bg.tuvarna.universitytimetable.dto.model.RoomListModel;
import bg.tuvarna.universitytimetable.dto.model.TeacherListModel;
import bg.tuvarna.universitytimetable.entity.enums.*;
import bg.tuvarna.universitytimetable.service.FacultyService;
import bg.tuvarna.universitytimetable.service.RoomService;
import bg.tuvarna.universitytimetable.service.SubjectService;
import bg.tuvarna.universitytimetable.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/subject")
public class SubjectController extends BaseController {

    private final SubjectService subjectService;
    private final FacultyService facultyService;
    private final RoomService roomService;
    private final TeacherService teacherService;

    @Autowired
    public SubjectController(SubjectService subjectService,
                             FacultyService facultyService,
                             RoomService roomService,
                             TeacherService teacherService) {
        this.subjectService = subjectService;
        this.facultyService = facultyService;
        this.roomService = roomService;
        this.teacherService = teacherService;
    }

    @ModelAttribute("degrees")
    public Degree[] degrees() {
        return Degree.values();
    }

    @ModelAttribute("courseModes")
    public CourseMode[] courseModes() {
        return CourseMode.values();
    }

    @ModelAttribute("facultyList")
    public List<FacultyListModel> facultyList() {
        return facultyService.getList();
    }

    @ModelAttribute("courseWeeks")
    public CourseWeek[] courseWeeks() {
        return CourseWeek.values();
    }

    @ModelAttribute("subjectTypes")
    public SubjectType[] subjectTypes() {
        return SubjectType.values();
    }

    @ModelAttribute("courseYears")
    public CourseYear[] courseYears() {
        return CourseYear.values();
    }

    @ModelAttribute("rooms")
    public List<RoomListModel> rooms() {
        return roomService.getList();
    }

    @ModelAttribute("teachers")
    public List<TeacherListModel> teachers() {
        return teacherService.getList();
    }

    @GetMapping("/create")
    public ModelAndView createSubject(ModelAndView modelAndView) {
        CreateSubjectData createSubjectData = new CreateSubjectData();
//        CreateCourseData createCourseData = new CreateCourseData();
//        createCourseData.setTeacherId(1L);
//        createCourseData.setYear(CourseYear.II);
//        createCourseData.setRoomId(1L);
//        createCourseData.setWeek(CourseWeek.EVEN);
//        createCourseData.setMode(CourseMode.FULL_TIME);
//        createCourseData.setDegree(Degree.MASTER_BG_2_5);
//        createCourseData.setSpecialtyId(1L);
//        createCourseData.setFacultyId(1L);
//        createCourseData.setDepartmentId(1L);
//        createCourseData.setStartWeek((short) 1);
//        createCourseData.setEndWeek((short) 1);
//        createCourseData.setMeetingsPerWeek((short) 1);
//        createCourseData.setHoursPerWeek((short) 1);
//        createCourseData.setGroups(List.of("Test1", "Test2", "Test3"));
        createSubjectData.setCourses(List.of(/*createCourseData*/));
        modelAndView.addObject("createSubjectData", createSubjectData);
        return view("subject/create", modelAndView);
    }

    @PostMapping("/create")
    public ModelAndView createSubject(@Valid @ModelAttribute("createSubjectData") CreateSubjectData createSubjectData,
                                       BindingResult bindingResult) {
        if (subjectService.createSubject(createSubjectData, bindingResult)) {
            return redirect("/subject/create");
        }
        return view("subject/create");
    }
}
