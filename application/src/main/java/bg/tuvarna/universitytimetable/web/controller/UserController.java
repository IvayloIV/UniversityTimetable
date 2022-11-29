package bg.tuvarna.universitytimetable.web.controller;

import bg.tuvarna.universitytimetable.dto.data.UpdatePasswordData;
import bg.tuvarna.universitytimetable.entity.User;
import bg.tuvarna.universitytimetable.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return view("user/login");
    }

    @GetMapping("/password/update")
    public ModelAndView updatePassword(@AuthenticationPrincipal User user, ModelAndView modelAndView) {
        if (user.getPasswordUpdatedDate() != null) {
            return redirect("/");
        }
        modelAndView.addObject("updatePasswordData", new UpdatePasswordData());
        return view("user/password", modelAndView);
    }

    @PostMapping("/password/update")
    public ModelAndView updatePassword(@Valid @ModelAttribute("updatePasswordData") UpdatePasswordData updatePasswordData,
                                       BindingResult bindingResult,
                                       @AuthenticationPrincipal User user) {
        if (userService.updateUserPassword(updatePasswordData, bindingResult, user)) {
            return redirect("/");
        }
        return super.view("user/password");
    }
}
