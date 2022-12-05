package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.data.CreateSpecialtyData;
import bg.tuvarna.universitytimetable.dto.model.SpecialtyListModel;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface SpecialtyService {

    List<SpecialtyListModel> getList(Long facultyId, Long departmentId);

    boolean createSpecialty(CreateSpecialtyData createSpecialtyData, BindingResult bindingResult);

    void delete(Long id);
}
