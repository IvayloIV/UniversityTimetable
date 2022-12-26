
package bg.tuvarna.universitytimetable.web.controller;

import bg.tuvarna.universitytimetable.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        return List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
    }

    @GetMapping("/generate")
    public ModelAndView getGeneratedSchedules(ModelAndView modelAndView) {
        modelAndView.addObject("faculties", scheduleService.findAll());
        return view("schedule/list", modelAndView);
    }

    @PostMapping("/save")
    public ModelAndView saveSchedules() {
        scheduleService.save();
        return redirect("/schedule/generate");
    }
}
