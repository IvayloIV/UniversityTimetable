package bg.tuvarna.universitytimetable.mapper;

import bg.tuvarna.universitytimetable.dto.data.CreateTeacherData;
import bg.tuvarna.universitytimetable.dto.data.TeacherFreeTimeData;
import bg.tuvarna.universitytimetable.dto.model.TeacherListModel;
import bg.tuvarna.universitytimetable.entity.Teacher;
import bg.tuvarna.universitytimetable.entity.TeacherFreeTime;
import bg.tuvarna.universitytimetable.entity.enums.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.DayOfWeek;
import java.util.List;

@Mapper(componentModel = "spring",
        imports = {Role.class, DayOfWeek.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TeacherMapper {

    @Autowired
    protected PasswordEncoder encoder;

    @Mappings({
        @Mapping(source = "createTeacherData.email", target = "user.email"),
        @Mapping(target = "user.role", expression = "java(Role.TEACHER)"),
        @Mapping(target = "user.password", expression = "java(encoder.encode(createTeacherData.getUcn()))"),
        @Mapping(target = "archived", constant = "false")
    })
    public abstract Teacher modelToEntity(CreateTeacherData createTeacherData);

    @Mapping(target = "day", expression = "java(DayOfWeek.of(createTeacherData.getDay()))")
    public abstract TeacherFreeTime modelToEntity(TeacherFreeTimeData createTeacherData);

    public abstract List<TeacherListModel> entityToModel(List<Teacher> teacher);

    @Mappings({
        @Mapping(source = "teacher.user.email", target = "email"),
        @Mapping(source = "teacher.ucn", target = "ucn"),
        @Mapping(source = "academicRankBg", target = "academicRank"),
        @Mapping(source = "firstNameBg", target = "firstName"),
        @Mapping(source = "lastNameBg", target = "lastName"),
        @Mapping(source = "teacher.user.passwordUpdatedDate", target = "passwordUpdate"),
    })
    public abstract TeacherListModel entityToModel(Teacher teacher);
}
