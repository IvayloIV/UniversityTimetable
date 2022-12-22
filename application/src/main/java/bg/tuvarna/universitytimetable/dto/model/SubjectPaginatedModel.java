package bg.tuvarna.universitytimetable.dto.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SubjectPaginatedModel {

    private Integer currentPage;

    private Integer totalPages;

    private List<SubjectListModel> subjects;
}
