package bg.tuvarna.universitytimetable.dto.data;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdatePasswordData {

    private String oldPassword;

    @NotNull
    @Size(min = 4, message = "{passwordUpdate.minLengthValidation}")
    private String newPassword;
    private String confirmPassword;
}
