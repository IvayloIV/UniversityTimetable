package bg.tuvarna.universitytimetable.mapper;

import bg.tuvarna.universitytimetable.dto.model.FacultyListModel;
import bg.tuvarna.universitytimetable.entity.Faculty;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FacultyMapper {

    List<FacultyListModel> entityToModel(List<Faculty> faculties);
}
