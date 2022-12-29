package bg.tuvarna.universitytimetable.web.controller;

import bg.tuvarna.universitytimetable.dto.data.CreateSubjectData;
import bg.tuvarna.universitytimetable.dto.data.SubjectSearchData;
import bg.tuvarna.universitytimetable.dto.model.FacultyListModel;
import bg.tuvarna.universitytimetable.dto.model.RoomListModel;
import bg.tuvarna.universitytimetable.dto.model.TeacherListModel;
import bg.tuvarna.universitytimetable.entity.enums.*;
import bg.tuvarna.universitytimetable.service.FacultyService;
import bg.tuvarna.universitytimetable.service.RoomService;
import bg.tuvarna.universitytimetable.service.SubjectService;
import bg.tuvarna.universitytimetable.service.TeacherService;
import bg.tuvarna.universitytimetable.util.DayOfWeekUtil;
import bg.tuvarna.universitytimetable.util.QueryParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/subject")
public class SubjectController extends BaseController {

    private final SubjectService subjectService;
    private final FacultyService facultyService;
    private final RoomService roomService;
    private final TeacherService teacherService;
    private final QueryParamsUtil queryParamsUtil;

    @Autowired
    public SubjectController(SubjectService subjectService,
                             FacultyService facultyService,
                             RoomService roomService,
                             TeacherService teacherService,
                             QueryParamsUtil queryParamsUtil) {
        this.subjectService = subjectService;
        this.facultyService = facultyService;
        this.roomService = roomService;
        this.teacherService = teacherService;
        this.queryParamsUtil = queryParamsUtil;
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

    @ModelAttribute("dayOfWeek")
    public String[] dayOfWeek() {
        return DayOfWeekUtil.getLocaleDays();
    }

    @GetMapping("/create")
    public ModelAndView createSubject(ModelAndView modelAndView) {
        CreateSubjectData createSubjectData = new CreateSubjectData();
        createSubjectData.setCourses(List.of());
        modelAndView.addObject("createSubjectData", createSubjectData);
        return view("subject/create", modelAndView);
    }

    @PostMapping("/create")
    public ModelAndView createSubject(@Valid @ModelAttribute("createSubjectData") CreateSubjectData createSubjectData,
                                       BindingResult bindingResult) {
        if (subjectService.createSubject(createSubjectData, bindingResult)) {
            return redirect("/subject/list");
        }
        return view("subject/create");
    }

    @GetMapping("/list")
    public ModelAndView getList(SubjectSearchData searchData, ModelAndView modelAndView) {
        modelAndView.addObject("subjectPaginatedModel", subjectService.getList(searchData));
        return view("subject/list", modelAndView);
    }

    @PostMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id,
                               SubjectSearchData searchData,
                               RedirectAttributes redirectAttributes) {
        subjectService.archiveSubject(id);
        queryParamsUtil.attachQueryParams(redirectAttributes, searchData);
        return redirect("/subject/list");
    }
}
