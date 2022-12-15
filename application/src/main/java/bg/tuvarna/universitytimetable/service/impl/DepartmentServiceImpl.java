package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.dto.model.DepartmentListModel;
import bg.tuvarna.universitytimetable.entity.Department;
import bg.tuvarna.universitytimetable.mapper.DepartmentMapper;
import bg.tuvarna.universitytimetable.repository.DepartmentRepository;
import bg.tuvarna.universitytimetable.service.DepartmentService;
import bg.tuvarna.universitytimetable.util.ResourceBundleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final ResourceBundleUtil resourceBundleUtil;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 DepartmentMapper departmentMapper,
                                 ResourceBundleUtil resourceBundleUtil) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.resourceBundleUtil = resourceBundleUtil;
    }

    @Override
    public DepartmentListModel getById(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException(
                        resourceBundleUtil.getMessage("createCourse.departmentNotFound")));

        return departmentMapper.entityToModel(department);
    }

    @Override
    public List<DepartmentListModel> getList(Long facultyId) {
        List<Department> departments = departmentRepository.findAllByFaculty_IdOrderByNameBg(facultyId);
        return departmentMapper.entityToModel(departments);
    }
}
