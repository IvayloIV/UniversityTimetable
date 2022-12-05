package bg.tuvarna.universitytimetable.dto.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class SpecialtyListModel {

    private Long id;
    private String facultyName;
    private String departmentName;
    private String specialtyName;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime createdDate;
}
