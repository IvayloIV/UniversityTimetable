package bg.tuvarna.universitytimetable.web.controller.rest;

import bg.tuvarna.universitytimetable.dto.model.DepartmentListModel;
import bg.tuvarna.universitytimetable.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentRestController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentRestController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}")
    public DepartmentListModel getById(@PathVariable Long id) {
        return departmentService.getById(id);
    }

    @GetMapping("/faculty/{id}")
    public List<DepartmentListModel> getList(@PathVariable Long id) {
        return departmentService.getList(id);
    }
}
