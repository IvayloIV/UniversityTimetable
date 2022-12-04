package bg.tuvarna.universitytimetable.dto.data;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CreateTeacherData {

    @NotNull
    @Size(min = 5, message = "{createTeacher.emailLengthValidation}")
    private String email;

    @NotNull
    @Size(min = 10, max = 10, message = "{createTeacher.ucnLengthValidation}")
    private String ucn;

    @NotNull
    @Size(min = 3, message = "{createTeacher.academicRankLengthValidation}")
    private String academicRankBg;

    @NotNull
    @Size(min = 3, message = "{createTeacher.academicRankLengthValidation}")
    private String academicRankEn;

    @NotNull
    @Size(min = 2, message = "{createTeacher.firstNameLengthValidation}")
    private String firstNameBg;

    @NotNull
    @Size(min = 2, message = "{createTeacher.firstNameLengthValidation}")
    private String firstNameEn;

    @NotNull
    @Size(min = 2, message = "{createTeacher.lastNameLengthValidation}")
    private String lastNameBg;

    @NotNull
    @Size(min = 2, message = "{createTeacher.lastNameLengthValidation}")
    private String lastNameEn;

    private List<TeacherFreeTimeData> teacherFreeTime;
}
