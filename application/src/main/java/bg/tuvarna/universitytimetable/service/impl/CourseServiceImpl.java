package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.entity.Course;
import bg.tuvarna.universitytimetable.repository.CourseRepository;
import bg.tuvarna.universitytimetable.service.CourseService;
import bg.tuvarna.universitytimetable.util.ResourceBundleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static bg.tuvarna.universitytimetable.repository.specification.CourseSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ResourceBundleUtil resourceBundleUtil;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository,
                             ResourceBundleUtil resourceBundleUtil) {
        this.courseRepository = courseRepository;
        this.resourceBundleUtil = resourceBundleUtil;
    }

    @Override
    public String updateActiveStatus(Long id) {
        Course course = findById(id);
        course.setActive(!course.getActive());
        courseRepository.save(course);
        return resourceBundleUtil.getMessage("courseList.statusUpdate");
    }

    @Override
    public String archiveCourse(Long id) {
        Course course = findById(id);
        course.setArchived(true);
        courseRepository.save(course);
        return resourceBundleUtil.getMessage("courseList.archiveCourse");
    }

    @Override
    public List<Course> getCoursesBySubjectId(Long subjectId) {
        return courseRepository.findAll(
                where(isArchived(false))
                    .and(isTeacherArchived(false))
                    .and(isSpecialtyArchived(false))
                    .and(withSubjectId(subjectId))
        );
    }

    private Course findById(Long id) {
        return courseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(resourceBundleUtil.getMessage("courseList.courseNotFound")));
    }
}
