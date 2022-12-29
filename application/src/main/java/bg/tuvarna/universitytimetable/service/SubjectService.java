package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.data.CreateSubjectData;
import bg.tuvarna.universitytimetable.dto.data.SubjectSearchData;
import bg.tuvarna.universitytimetable.dto.model.SubjectPaginatedModel;
import org.springframework.validation.BindingResult;

public interface SubjectService {

    boolean createSubject(CreateSubjectData subjectData, BindingResult bindingResult);

    SubjectPaginatedModel getList(SubjectSearchData searchData);

    String updateActiveStatus(Long id);

    void archiveSubject(Long id);
}
