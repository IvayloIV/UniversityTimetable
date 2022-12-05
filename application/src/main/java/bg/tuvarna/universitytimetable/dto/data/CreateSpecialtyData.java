package bg.tuvarna.universitytimetable.dto.data;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateSpecialtyData {

    @NotNull
    private Long facultyId;

    private Long departmentId;

    @NotNull
    @Size(min = 1, message = "{createSpecialty.nameLengthValidation}")
    private String nameBg;

    @NotNull
    @Size(min = 1, message = "{createSpecialty.nameLengthValidation}")
    private String nameEn;
}
