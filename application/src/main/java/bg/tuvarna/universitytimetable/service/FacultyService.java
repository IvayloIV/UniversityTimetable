package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.model.FacultyListModel;

import java.util.List;

public interface FacultyService {

    List<FacultyListModel> getList();
}
