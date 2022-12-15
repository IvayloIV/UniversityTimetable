package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.dto.data.CreateSpecialtyData;
import bg.tuvarna.universitytimetable.dto.model.FacultyListModel;
import bg.tuvarna.universitytimetable.dto.model.SpecialtyListModel;
import bg.tuvarna.universitytimetable.entity.Department;
import bg.tuvarna.universitytimetable.entity.Specialty;
import bg.tuvarna.universitytimetable.exception.ValidationException;
import bg.tuvarna.universitytimetable.mapper.SpecialtyMapper;
import bg.tuvarna.universitytimetable.repository.DepartmentRepository;
import bg.tuvarna.universitytimetable.repository.SpecialtyRepository;
import bg.tuvarna.universitytimetable.service.FacultyService;
import bg.tuvarna.universitytimetable.service.SpecialtyService;
import bg.tuvarna.universitytimetable.util.ResourceBundleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;
    private final DepartmentRepository departmentRepository;
    private final FacultyService facultyService;
    private final ResourceBundleUtil resourceBundleUtil;

    @Autowired
    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository,
                                SpecialtyMapper specialtyMapper,
                                DepartmentRepository departmentRepository,
                                FacultyService facultyService,
                                ResourceBundleUtil resourceBundleUtil) {
        this.specialtyRepository = specialtyRepository;
        this.specialtyMapper = specialtyMapper;
        this.departmentRepository = departmentRepository;
        this.facultyService = facultyService;
        this.resourceBundleUtil = resourceBundleUtil;
    }

    @Override
    public SpecialtyListModel getById(Long specialtyId) {
        Specialty specialty = specialtyRepository.findByIdAndArchivedFalse(specialtyId)
            .orElseThrow(() -> new IllegalArgumentException(
                    resourceBundleUtil.getMessage("createCourse.specialtyNotFound")));
        return specialtyMapper.entityToModel(specialty);
    }

    @Override
    public List<SpecialtyListModel> getList(Long departmentId) {
        List<Specialty> specialties = specialtyRepository.findByDepartmentId(departmentId);
        return specialtyMapper.entityToModel(specialties);
    }

    @Override
    public boolean createSpecialty(CreateSpecialtyData createSpecialtyData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return false;
        }

        Department department = departmentRepository.findById(createSpecialtyData.getDepartmentId())
            .orElseThrow(() -> {
                String message = resourceBundleUtil.getMessage("createSpecialty.departmentNotFound");
                List<FacultyListModel> facultyList = facultyService.getList();
                throw new ValidationException(message, "specialty/create", Map.of("facultyList", facultyList));
            });

        Specialty specialty = specialtyMapper.modelToEntity(createSpecialtyData);
        specialty.setDepartment(department);
        specialtyRepository.save(specialty);
        return true;
    }

    @Override
    public void delete(Long id) {
        Specialty specialty = specialtyRepository.findById(id) //TODO: After home page is created, check whether model should be returned
                .orElseThrow(() -> new ValidationException(resourceBundleUtil.getMessage("createSpecialty.notFound"), "/"));
        specialty.setArchived(true);
        specialtyRepository.save(specialty);
    }
}
