package bg.tuvarna.universitytimetable.dto.data;

import lombok.Data;

@Data
public class SubjectSearchData {

    private String name;

    private String subjectType;

    private Long facultyId;

    private Long departmentId;

    private Long specialtyId;

    private Long teacherId;

    private Integer page = 1;
}
