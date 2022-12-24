package bg.tuvarna.universitytimetable.dto.model;

import lombok.Data;

import java.util.List;

@Data
public class FacultyScheduleModel {

    private String nameBg;

    private List<CourseScheduleModel> courses;
}
