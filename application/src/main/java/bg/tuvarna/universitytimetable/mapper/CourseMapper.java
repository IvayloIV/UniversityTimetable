package bg.tuvarna.universitytimetable.mapper;

import bg.tuvarna.universitytimetable.dto.data.CreateCourseData;
import bg.tuvarna.universitytimetable.dto.data.CreateSubjectData;
import bg.tuvarna.universitytimetable.entity.Course;
import bg.tuvarna.universitytimetable.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {

    @Mappings({
        @Mapping(target = "active", constant = "true"),
        @Mapping(target = "archived", constant = "false"),
        @Mapping(target = "groups", ignore = true)
    })
    Course modelToEntity(CreateCourseData createCourseData);
}
