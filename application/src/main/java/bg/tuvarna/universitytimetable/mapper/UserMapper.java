package bg.tuvarna.universitytimetable.mapper;

import bg.tuvarna.universitytimetable.dto.data.UpdatePasswordData;
import bg.tuvarna.universitytimetable.entity.User;
import org.mapstruct.*;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",
        imports = {LocalDateTime.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mappings({
        @Mapping(source = "model.newPassword", target = "password"),
        @Mapping(target = "passwordUpdatedDate", expression = "java(LocalDateTime.now())")
    })
    void updateEntityFromModel(UpdatePasswordData model, @MappingTarget User entity);
}
