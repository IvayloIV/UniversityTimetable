package bg.tuvarna.universitytimetable.repository.specification;

import bg.tuvarna.universitytimetable.entity.Course;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class CourseSpecification {

    public static Specification<Course> isArchived(Boolean archived) {
        if (ObjectUtils.isEmpty(archived)) {
            return null;
        } else {
            return (course, cq, cb) -> cb.equal(course.get("archived"), archived);
        }
    }

    public static Specification<Course> isActive(Boolean active) {
        if (ObjectUtils.isEmpty(active)) {
            return null;
        } else {
            return (course, cq, cb) -> cb.equal(course.get("active"), active);
        }
    }

    public static Specification<Course> isTeacherArchived(Boolean archived) {
        if (ObjectUtils.isEmpty(archived)) {
            return null;
        } else {
            return (course, cq, cb) -> cb.equal(course.get("teacher").get("archived"), archived);
        }
    }

    public static Specification<Course> isSpecialtyArchived(Boolean archived) {
        if (ObjectUtils.isEmpty(archived)) {
            return null;
        } else {
            return (course, cq, cb) -> cb.equal(course.get("specialty").get("archived"), archived);
        }
    }

    public static Specification<Course> isSubjectArchived(Boolean archived) {
        if (ObjectUtils.isEmpty(archived)) {
            return null;
        } else {
            return (course, cq, cb) -> cb.equal(course.get("subject").get("archived"), archived);
        }
    }

    public static Specification<Course> isSubjectActive(Boolean active) {
        if (ObjectUtils.isEmpty(active)) {
            return null;
        } else {
            return (course, cq, cb) -> cb.equal(course.get("subject").get("active"), active);
        }
    }

    public static Specification<Course> withSubjectId(Long subjectId) {
        if (ObjectUtils.isEmpty(subjectId)) {
            return null;
        } else {
            return (course, cq, cb) -> cb.equal(course.get("subject").get("id"), subjectId);
        }
    }
}
