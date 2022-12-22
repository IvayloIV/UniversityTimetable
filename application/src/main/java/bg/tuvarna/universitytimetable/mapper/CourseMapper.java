package bg.tuvarna.universitytimetable.mapper;

import bg.tuvarna.universitytimetable.dto.data.CreateCourseData;
import bg.tuvarna.universitytimetable.dto.model.CourseListModel;
import bg.tuvarna.universitytimetable.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {CourseTimeMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {

    @Mappings({
        @Mapping(target = "active", constant = "true"),
        @Mapping(target = "archived", constant = "false"),
        @Mapping(target = "groups", ignore = true)
    })
    Course modelToEntity(CreateCourseData createCourseData);

    @Mappings({
            @Mapping(target = "faculty", source = "course.specialty.department.faculty.nameBg"),
            @Mapping(target = "department", source = "course.specialty.department.nameBg"),
            @Mapping(target = "specialty", source = "course.specialty.nameBg"),
            @Mapping(target = "room", source = "course.room.numberBg"),
            @Mapping(target = "teacher", expression = "java(course.getTeacher().getAcademicRankBg() + \" \" + " +
                            "course.getTeacher().getFirstNameBg() + \" \" + " +
                            "course.getTeacher().getLastNameBg())"),
    })
    CourseListModel entityToModel(Course course);
}
