
package bg.tuvarna.universitytimetable.web.controller;

import bg.tuvarna.universitytimetable.dto.data.ScheduleEditData;
import bg.tuvarna.universitytimetable.dto.data.StudentScheduleSearchData;
import bg.tuvarna.universitytimetable.dto.data.TeacherScheduleSearchData;
import bg.tuvarna.universitytimetable.service.ScheduleService;
import bg.tuvarna.universitytimetable.util.DayOfWeekUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.List;

@Controller
@RequestMapping("/schedule")
public class ScheduleController extends BaseController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @ModelAttribute("daysOfWeek")
    public List<DayOfWeek> daysOfWeek() {
        return DayOfWeekUtil.getWorkDays();
    }

    @GetMapping("/generate")
    public ModelAndView getGeneratedSchedules(ModelAndView modelAndView) {
        modelAndView.addObject("faculties", scheduleService.findAll());
        return view("schedule/list", modelAndView);
    }

    @PostMapping("/generate")
    public ModelAndView generatedSchedules() {
        scheduleService.generateSchedules();
        return redirect("/schedule/generate");
    }

    @PostMapping("/save")
    public ModelAndView saveSchedules() {
        scheduleService.save();
        return redirect("/schedule/generate");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editSchedule(@PathVariable Long id, ModelAndView modelAndView) {
        modelAndView.addObject("schedule", scheduleService.getEditModel(id));
        return view("schedule/edit", modelAndView);
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editSchedule(@PathVariable Long id,
                                     @ModelAttribute("scheduleEditData") ScheduleEditData scheduleEditData) {
        scheduleService.edit(id, scheduleEditData);
        return redirect("/schedule/generate");
    }

    @GetMapping("/list/students")
    public ModelAndView studentsSchedule(StudentScheduleSearchData studentScheduleSearchData, ModelAndView modelAndView) {
        scheduleService.loadStudentSchedule(studentScheduleSearchData, modelAndView);
        return view("schedule/students", modelAndView);
    }

    @GetMapping("/download/students")
    public ResponseEntity<Resource> downloadStudentSchedule(StudentScheduleSearchData studentScheduleSearchData) {
        return scheduleService.generateStudentSchedule(studentScheduleSearchData);
    }

    @PostMapping("/notify/students")
    public ModelAndView notifyStudents(@RequestParam("studentsNameCsv") MultipartFile studentsNameCsv,
                                       StudentScheduleSearchData studentScheduleSearchData,
                                       RedirectAttributes redirectAttributes) throws IOException {
        scheduleService.notifyStudents(studentsNameCsv, studentScheduleSearchData, redirectAttributes);
        return redirect("/schedule/list/students");
    }

    @GetMapping("/list/teachers")
    public ModelAndView teachersSchedule(TeacherScheduleSearchData teacherScheduleSearchData, ModelAndView modelAndView) {
        scheduleService.loadTeacherSchedule(teacherScheduleSearchData, modelAndView);
        return view("schedule/teachers", modelAndView);
    }

    @GetMapping("/download/teachers")
    public ResponseEntity<Resource> downloadTeacherSchedule(TeacherScheduleSearchData teacherScheduleSearchData) {
        return scheduleService.generateTeacherSchedule(teacherScheduleSearchData);
    }

    @PostMapping("/notify/teacher")
    public ModelAndView notifyTeacher(TeacherScheduleSearchData teacherScheduleSearchData,
                                       RedirectAttributes redirectAttributes) throws IOException {
        scheduleService.notifyTeacher(teacherScheduleSearchData, redirectAttributes);
        return redirect("/schedule/list/teachers");
    }
}
