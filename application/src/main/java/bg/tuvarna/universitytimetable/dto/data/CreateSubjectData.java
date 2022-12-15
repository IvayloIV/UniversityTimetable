package bg.tuvarna.universitytimetable.dto.data;

import bg.tuvarna.universitytimetable.entity.enums.SubjectType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CreateSubjectData {

    @NotNull
    @Size(min = 1, message = "{createSubject.nameLengthValidation}")
    private String nameBg;

    @NotNull
    @Size(min = 1, message = "{createSubject.nameLengthValidation}")
    private String nameEn;

    private SubjectType type;

    private List<CreateCourseData> courses;
}
