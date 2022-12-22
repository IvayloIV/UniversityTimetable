package bg.tuvarna.universitytimetable.repository.specification;

import bg.tuvarna.universitytimetable.entity.Course;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class CourseSpecification {

    public static Specification<Course> isArchived(Boolean archived) {
        if (ObjectUtils.isEmpty(archived)) {
            return null;
        } else {
            return (subject, cq, cb) -> cb.equal(subject.get("archived"), archived);
        }
    }

    public static Specification<Course> isTeacherArchived(Boolean archived) {
        if (ObjectUtils.isEmpty(archived)) {
            return null;
        } else {
            return (subject, cq, cb) -> cb.equal(subject.get("teacher").get("archived"), archived);
        }
    }

    public static Specification<Course> isSpecialtyArchived(Boolean archived) {
        if (ObjectUtils.isEmpty(archived)) {
            return null;
        } else {
            return (subject, cq, cb) -> cb.equal(subject.get("specialty").get("archived"), archived);
        }
    }

    public static Specification<Course> withSubjectId(Long subjectId) {
        if (ObjectUtils.isEmpty(subjectId)) {
            return null;
        } else {
            return (subject, cq, cb) -> cb.equal(subject.get("subject").get("id"), subjectId);
        }
    }
}
