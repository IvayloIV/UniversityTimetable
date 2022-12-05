package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.model.DepartmentListModel;

import java.util.List;

public interface DepartmentService {

    List<DepartmentListModel> getList(Long facultyId);
}
