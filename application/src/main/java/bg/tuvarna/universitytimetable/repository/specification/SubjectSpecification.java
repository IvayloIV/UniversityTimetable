package bg.tuvarna.universitytimetable.repository.specification;

import bg.tuvarna.universitytimetable.entity.Subject;
import bg.tuvarna.universitytimetable.entity.enums.SubjectType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;

public class SubjectSpecification {

    public static Specification<Subject> withName(String name) {
        if (ObjectUtils.isEmpty(name)) {
            return null;
        } else {
            return (subject, cq, cb) -> cb.like(cb.lower(subject.get("nameBg")),
                    "%" + name.toLowerCase() + "%");
        }
    }

    public static Specification<Subject> withType(String type) {
        if (ObjectUtils.isEmpty(type) || Arrays.stream(SubjectType.values()).noneMatch(t -> t.name().equals(type))) {
            return null;
        } else {
            return (subject, cq, cb) -> cb.equal(subject.get("type"), SubjectType.valueOf(type));
        }
    }

    public static Specification<Subject> withFacultyId(Long facultyId) {
        if (ObjectUtils.isEmpty(facultyId) || facultyId < 0) {
            return null;
        } else {
            return (subject, cq, cb) -> cb.equal(subject.join("courses")
                    .get("specialty").get("department").get("faculty").get("id"), facultyId);
        }
    }

    public static Specification<Subject> withDepartmentId(Long departmentId) {
        if (ObjectUtils.isEmpty(departmentId) || departmentId < 0) {
            return null;
        } else {
            return (subject, cq, cb) -> cb.equal(subject.join("courses")
                    .get("specialty").get("department").get("id"), departmentId);
        }
    }

    public static Specification<Subject> withSpecialtyId(Long specialtyId) {
        if (ObjectUtils.isEmpty(specialtyId) || specialtyId < 0) {
            return null;
        } else {
            return (subject, cq, cb) -> cb.equal(subject.join("courses")
                    .get("specialty").get("id"), specialtyId);
        }
    }

    public static Specification<Subject> withTeacherId(Long teacherId) {
        if (ObjectUtils.isEmpty(teacherId) || teacherId < 0) {
            return null;
        } else {
            return (subject, cq, cb) -> cb.equal(subject.join("courses")
                    .get("teacher").get("id"), teacherId);
        }
    }

    public static Specification<Subject> isArchived(Boolean archived) {
        if (ObjectUtils.isEmpty(archived)) {
            return null;
        } else {
            return (subject, cq, cb) -> cb.equal(subject.get("archived"), archived);
        }
    }
}
