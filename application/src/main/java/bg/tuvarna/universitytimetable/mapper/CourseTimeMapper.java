package bg.tuvarna.universitytimetable.mapper;

import bg.tuvarna.universitytimetable.dto.data.CourseTimeData;
import bg.tuvarna.universitytimetable.entity.CourseTime;
import bg.tuvarna.universitytimetable.entity.enums.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.DayOfWeek;

@Mapper(componentModel = "spring",
        imports = {Role.class, DayOfWeek.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseTimeMapper {

    @Mapping(target = "day", expression = "java(DayOfWeek.of(courseTimeData.getDay()))")
    CourseTime modelToEntity(CourseTimeData courseTimeData);
}
