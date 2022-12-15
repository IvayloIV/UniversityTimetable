package bg.tuvarna.universitytimetable.mapper;

import bg.tuvarna.universitytimetable.dto.data.CreateSubjectData;
import bg.tuvarna.universitytimetable.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {CourseMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubjectMapper {

    @Mappings({
        @Mapping(target = "active", constant = "true"),
        @Mapping(target = "archived", constant = "false")
    })
    Subject modelToEntity(CreateSubjectData createSubjectData);
}
