package bg.tuvarna.universitytimetable.web.controller;

import bg.tuvarna.universitytimetable.dto.data.CreateSpecialtyData;
import bg.tuvarna.universitytimetable.dto.model.FacultyListModel;
import bg.tuvarna.universitytimetable.service.FacultyService;
import bg.tuvarna.universitytimetable.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/specialty")
public class SpecialtyController extends BaseController {

    private final SpecialtyService specialtyService;
    private final FacultyService facultyService;

    @Autowired
    public SpecialtyController(SpecialtyService specialtyService,
                               FacultyService facultyService) {
        this.specialtyService = specialtyService;
        this.facultyService = facultyService;
    }

    @ModelAttribute("facultyList")
    public List<FacultyListModel> facultyList() {
        return facultyService.getList();
    }

    @GetMapping("/create")
    public ModelAndView createSpecialty(ModelAndView modelAndView) {
        modelAndView.addObject("createSpecialtyData", new CreateSpecialtyData());
        return view("specialty/create", modelAndView);
    }

    @PostMapping("/create")
    public ModelAndView createSpecialty(@Valid @ModelAttribute("createSpecialtyData") CreateSpecialtyData createSpecialtyData,
                                       BindingResult bindingResult) {
        if (specialtyService.createSpecialty(createSpecialtyData, bindingResult)) {
            return redirect("/specialty/create");
        }
        return view("specialty/create");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteSpecialty(@PathVariable Long id) {
        specialtyService.delete(id);
        return redirect("/specialty/create");
    }
}
