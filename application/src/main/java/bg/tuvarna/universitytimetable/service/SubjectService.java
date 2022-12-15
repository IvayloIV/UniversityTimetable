package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.data.CreateSubjectData;
import org.springframework.validation.BindingResult;

public interface SubjectService {

    boolean createSubject(CreateSubjectData subjectData, BindingResult bindingResult);
}
