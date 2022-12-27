
package bg.tuvarna.universitytimetable.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {

    @GetMapping
    public ModelAndView home() {
        return redirect("/schedule/list/students");
    }
}
