package bg.tuvarna.universitytimetable.web.controller.rest;

import bg.tuvarna.universitytimetable.dto.model.SpecialtyListModel;
import bg.tuvarna.universitytimetable.service.SpecialtyService;
import bg.tuvarna.universitytimetable.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/specialty")
public class SpecialtyRestController {

    private final SpecialtyService specialtyService;

    @Autowired
    public SpecialtyRestController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping("/faculty/{facultyId}/department/{departmentId}")
    public List<SpecialtyListModel> getList(@PathVariable Long facultyId, @PathVariable Long departmentId) {
        return specialtyService.getList(facultyId, departmentId);
    }
}
