package bg.tuvarna.universitytimetable.dto.model;

import bg.tuvarna.universitytimetable.entity.enums.SubjectType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SubjectListModel {

    private Long id;

    private String name;

    private SubjectType type;

    private Boolean active;

    private LocalDateTime createdDate;

    private List<CourseListModel> courses;
}
