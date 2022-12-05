package bg.tuvarna.universitytimetable.mapper;

import bg.tuvarna.universitytimetable.dto.data.CreateSpecialtyData;
import bg.tuvarna.universitytimetable.dto.model.SpecialtyListModel;
import bg.tuvarna.universitytimetable.entity.Specialty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpecialtyMapper {

    @Mapping(target = "archived", constant = "false")
    Specialty modelToEntity(CreateSpecialtyData createSpecialtyData);

    List<SpecialtyListModel> entityToModel(List<Specialty> specialties);

    @Mappings({
        @Mapping(source = "specialty.department.faculty.nameBg", target = "facultyName"),
        @Mapping(source = "specialty.department.nameBg", target = "departmentName"),
        @Mapping(source = "specialty.nameBg", target = "specialtyName"),
    })
    SpecialtyListModel entityToModel(Specialty specialty);
}
