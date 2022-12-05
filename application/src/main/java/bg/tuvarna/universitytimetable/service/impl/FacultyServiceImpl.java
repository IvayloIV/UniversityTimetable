package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.dto.model.FacultyListModel;
import bg.tuvarna.universitytimetable.entity.Faculty;
import bg.tuvarna.universitytimetable.mapper.FacultyMapper;
import bg.tuvarna.universitytimetable.repository.FacultyRepository;
import bg.tuvarna.universitytimetable.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository, FacultyMapper facultyMapper) {
        this.facultyRepository = facultyRepository;
        this.facultyMapper = facultyMapper;
    }

    @Override
    public List<FacultyListModel> getList() {
        List<Faculty> faculties = facultyRepository.findAllByOrderByNameBg();
        return facultyMapper.entityToModel(faculties);
    }
}
