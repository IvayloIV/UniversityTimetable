package bg.tuvarna.universitytimetable.dto.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class TeacherListData {

    private Long id;
    private String email;
    private String ucn;
    private String academicRank;
    private String firstName;
    private String lastName;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime passwordUpdate;
}
