package bg.tuvarna.universitytimetable.web.controller.rest;

import bg.tuvarna.universitytimetable.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subject")
public class SubjectRestController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectRestController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PatchMapping("/update/status/{id}")
    public String updateStatus(@PathVariable Long id) {
        return subjectService.updateActiveStatus(id);
    }
}
