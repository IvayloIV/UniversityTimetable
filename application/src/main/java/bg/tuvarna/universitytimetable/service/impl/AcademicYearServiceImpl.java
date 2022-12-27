package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.entity.enums.Semester;
import bg.tuvarna.universitytimetable.repository.AcademicYearRepository;
import bg.tuvarna.universitytimetable.service.AcademicYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class AcademicYearServiceImpl implements AcademicYearService {

    private final AcademicYearRepository academicYearRepository;

    @Autowired
    public AcademicYearServiceImpl(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }

    @Override
    public SortedSet<String> getYears() {
        return academicYearRepository.findAll()
            .stream()
            .map(y -> y.getSemester().equals(Semester.WINTER) ?
                    String.format("%s/%s", y.getYear(), y.getYear() + 1) :
                    String.format("%s/%s", y.getYear() - 1, y.getYear()))
            .collect(Collectors.toCollection(TreeSet::new));
    }
}
