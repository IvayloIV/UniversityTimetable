package bg.tuvarna.universitytimetable.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        return view("user/login", modelAndView);
    }
}
