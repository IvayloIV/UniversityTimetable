package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.entity.AcademicYear;

import java.util.SortedSet;

public interface AcademicYearService {

    SortedSet<String> getYears();

    AcademicYear getLastAcademicYear();
}
