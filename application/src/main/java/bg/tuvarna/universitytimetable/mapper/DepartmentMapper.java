package bg.tuvarna.universitytimetable.mapper;

import bg.tuvarna.universitytimetable.dto.model.DepartmentListModel;
import bg.tuvarna.universitytimetable.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper {

    DepartmentListModel entityToModel(Department departments);

    List<DepartmentListModel> entityToModel(List<Department> departments);
}
