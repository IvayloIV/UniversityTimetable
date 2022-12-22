package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.entity.Course;

import java.util.List;

public interface CourseService {

    String updateActiveStatus(Long id);

    String archiveCourse(Long id);

    List<Course> getCoursesBySubjectId(Long subjectId);
}
