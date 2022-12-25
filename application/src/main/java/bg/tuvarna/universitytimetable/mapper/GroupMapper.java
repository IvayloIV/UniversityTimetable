package bg.tuvarna.universitytimetable.mapper;

import bg.tuvarna.universitytimetable.dto.model.GroupScheduleModel;
import bg.tuvarna.universitytimetable.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupMapper {

    List<GroupScheduleModel> entityToModel(List<Group> groups);
}
