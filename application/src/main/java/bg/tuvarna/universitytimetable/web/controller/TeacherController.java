package bg.tuvarna.universitytimetable.web.controller;

import bg.tuvarna.universitytimetable.dto.data.CreateTeacherData;
import bg.tuvarna.universitytimetable.dto.model.TeacherListData;
import bg.tuvarna.universitytimetable.service.TeacherService;
import bg.tuvarna.universitytimetable.util.DayOfWeekUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController extends BaseController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @ModelAttribute("dayOfWeek")
    public String[] dayOfWeek() {
        return DayOfWeekUtil.getLocaleDays();
    }

    @ModelAttribute("teacherListData")
    public List<TeacherListData> teacherListData() {
        return teacherService.getList();
    }

    @GetMapping("/create")
    public ModelAndView createTeacher(ModelAndView modelAndView) {
        CreateTeacherData createTeacherData = new CreateTeacherData();
        createTeacherData.setTeacherFreeTime(List.of());
        modelAndView.addObject("createTeacherData", createTeacherData);
        return view("teacher/create", modelAndView);
    }

    @PostMapping("/create")
    public ModelAndView createTeacher(@Valid @ModelAttribute("createTeacherData") CreateTeacherData createTeacherData,
                                       BindingResult bindingResult) {
        if (teacherService.createTeacher(createTeacherData, bindingResult)) {
            return redirect("/teacher/create");
        }
        return view("teacher/create");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView createTeacher(@PathVariable Long id) {
        teacherService.delete(id);
        return redirect("/teacher/create");
    }
}
