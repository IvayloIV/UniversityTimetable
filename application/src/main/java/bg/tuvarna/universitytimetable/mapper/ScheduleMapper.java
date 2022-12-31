package bg.tuvarna.universitytimetable.mapper;

import bg.tuvarna.universitytimetable.dto.data.ScheduleEditData;
import bg.tuvarna.universitytimetable.dto.model.*;
import bg.tuvarna.universitytimetable.entity.AcademicYear;
import bg.tuvarna.universitytimetable.entity.Schedule;
import bg.tuvarna.universitytimetable.entity.enums.Semester;
import org.mapstruct.*;

import java.time.DayOfWeek;

@Mapper(componentModel = "spring",
        imports = {DayOfWeek.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {

    @Mappings({
        @Mapping(target = "semester", source = "schedule.academicYear.semester"),
        @Mapping(target = "departmentNameBg", source = "schedule.course.specialty.department.nameBg"),
        @Mapping(target = "departmentNameEn", source = "schedule.course.specialty.department.nameEn"),
        @Mapping(target = "specialtyNameBg", source = "schedule.course.specialty.nameBg"),
        @Mapping(target = "specialtyNameEn", source = "schedule.course.specialty.nameEn"),
        @Mapping(target = "degree", source = "schedule.course.degree"),
        @Mapping(target = "courseYear", source = "schedule.course.year"),
        @Mapping(target = "mode", source = "schedule.course.mode")
    })
    CourseScheduleModel entityToCourseModel(Schedule schedule);

    @Mappings({
        @Mapping(target = "id", source = "schedule.id"),
        @Mapping(target = "day", source = "schedule.day"),
        @Mapping(target = "startTime", source = "schedule.startTime"),
        @Mapping(target = "endTime", source = "schedule.endTime"),
        @Mapping(target = "hexColor", source = "schedule.hexColor"),
        @Mapping(target = "subjectType", source = "schedule.course.subject.type"),
        @Mapping(target = "subjectNameBg", source = "schedule.course.subject.nameBg"),
        @Mapping(target = "subjectNameEn", source = "schedule.course.subject.nameEn"),
        @Mapping(target = "roomNumberBg", source = "schedule.course.room.numberBg"),
        @Mapping(target = "roomNumberEn", source = "schedule.course.room.numberEn"),
        @Mapping(target = "week", source = "schedule.course.week"),
        @Mapping(target = "startWeek", source = "schedule.course.startWeek"),
        @Mapping(target = "endWeek", source = "schedule.course.endWeek"),
        @Mapping(target = "group", source = "schedule.group"),
        @Mapping(target = "teacherNameBg", expression = "java(schedule.getCourse().getTeacher().getAcademicRankBg() + \" \" + " +
                "schedule.getCourse().getTeacher().getFirstNameBg() + \" \" + " +
                "schedule.getCourse().getTeacher().getLastNameBg())"),
        @Mapping(target = "teacherNameEn", expression = "java(schedule.getCourse().getTeacher().getAcademicRankEn() + \" \" + " +
                "schedule.getCourse().getTeacher().getFirstNameEn() + \" \" + " +
                "schedule.getCourse().getTeacher().getLastNameEn())"),
    })
    ScheduleDetailsModel entityToScheduleModel(Schedule schedule);

    @Mappings({
        @Mapping(target = "id", source = "schedule.id"),
        @Mapping(target = "degree", source = "schedule.course.degree"),
        @Mapping(target = "facultyName", source = "schedule.course.specialty.department.faculty.nameBg"),
        @Mapping(target = "departmentName", source = "schedule.course.specialty.department.nameBg"),
        @Mapping(target = "specialtyName", source = "schedule.course.specialty.nameBg"),
        @Mapping(target = "year", source = "schedule.course.year"),
        @Mapping(target = "mode", source = "schedule.course.mode"),
        @Mapping(target = "groupName", source = "schedule.group.name"),
        @Mapping(target = "subjectName", source = "schedule.course.subject.nameBg"),
        @Mapping(target = "subjectType", source = "schedule.course.subject.type"),
        @Mapping(target = "day", source = "schedule.day.value"),
        @Mapping(target = "startTime", source = "schedule.startTime"),
        @Mapping(target = "endTime", source = "schedule.endTime")
    })
    ScheduleEditModel entityToEditModel(Schedule schedule);

    @Mapping(target = "day", expression = "java(DayOfWeek.of(scheduleEditData.getDay()))")
    void updateSchedule(ScheduleEditData scheduleEditData, @MappingTarget Schedule schedule);

    void updateScheduleModel(ScheduleEditData scheduleEditData, @MappingTarget ScheduleEditModel scheduleEditModel);

    @Mappings({
        @Mapping(target = "day", source = "schedule.day"),
        @Mapping(target = "startTime", source = "schedule.startTime"),
        @Mapping(target = "endTime", source = "schedule.endTime"),
        @Mapping(target = "subjectType", source = "schedule.course.subject.type"),
        @Mapping(target = "subjectNameBg", source = "schedule.course.subject.nameBg"),
        @Mapping(target = "roomNumberBg", source = "schedule.course.room.numberBg"),
        @Mapping(target = "week", source = "schedule.course.week")
    })
    TeacherScheduleModel entityToTeacherScheduleModel(Schedule schedule);

    @Mappings({
        @Mapping(target = "degree", source = "schedule.course.degree"),
        @Mapping(target = "specialtyNameBg", source = "schedule.course.specialty.nameBg"),
        @Mapping(target = "courseYear", source = "schedule.course.year"),
        @Mapping(target = "mode", source = "schedule.course.mode"),
        @Mapping(target = "week", source = "schedule.course.week"),
        @Mapping(target = "startWeek", source = "schedule.course.startWeek"),
        @Mapping(target = "endWeek", source = "schedule.course.endWeek")
    })
    TeacherCourseModel entityToTeacherCourseModel(Schedule schedule);

    @AfterMapping
    default void updateAcademicYear(Schedule schedule, @MappingTarget CourseScheduleModel scheduleModel) {
        AcademicYear academicYear = schedule.getAcademicYear();
        Short year = academicYear.getYear();
        Semester semester = academicYear.getSemester();

        if (semester.equals(Semester.SUMMER)) {
            scheduleModel.setYear(String.format("%s/%s", year - 1, year));
        } else {
            scheduleModel.setYear(String.format("%s/%s", year, year + 1));
        }
    }
}
