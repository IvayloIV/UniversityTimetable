package bg.tuvarna.universitytimetable.web.controller.rest;

import bg.tuvarna.universitytimetable.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseRestController {

    private final CourseService courseService;

    @Autowired
    public CourseRestController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PatchMapping("/update/status/{id}")
    public String updateStatus(@PathVariable Long id) {
        return courseService.updateActiveStatus(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        return courseService.archiveCourse(id);
    }
}
