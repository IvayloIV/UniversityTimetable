package bg.tuvarna.universitytimetable.dto.model;

import lombok.Data;

@Data
public class TeacherScheduleFilterModel {

    private Long id;
    private String academicRank;
    private String firstName;
    private String lastName;
}
