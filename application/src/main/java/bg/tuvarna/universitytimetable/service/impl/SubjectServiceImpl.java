package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.dto.data.CreateCourseData;
import bg.tuvarna.universitytimetable.dto.data.CreateSubjectData;
import bg.tuvarna.universitytimetable.entity.*;
import bg.tuvarna.universitytimetable.entity.enums.*;
import bg.tuvarna.universitytimetable.exception.ValidationException;
import bg.tuvarna.universitytimetable.mapper.SubjectMapper;
import bg.tuvarna.universitytimetable.repository.*;
import bg.tuvarna.universitytimetable.service.FacultyService;
import bg.tuvarna.universitytimetable.service.RoomService;
import bg.tuvarna.universitytimetable.service.SubjectService;
import bg.tuvarna.universitytimetable.service.TeacherService;
import bg.tuvarna.universitytimetable.util.ResourceBundleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SpecialtyRepository specialtyRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final RoomRepository roomRepository;
    private final ResourceBundleUtil resourceBundleUtil;
    private final SubjectMapper subjectMapper;
    private final RoomService roomService;
    private final TeacherService teacherService;
    private final FacultyService facultyService;

    @Autowired
    public SubjectServiceImpl(SpecialtyRepository specialtyRepository,
                              SubjectRepository subjectRepository,
                              TeacherRepository teacherRepository,
                              GroupRepository groupRepository,
                              RoomRepository roomRepository,
                              ResourceBundleUtil resourceBundleUtil,
                              SubjectMapper subjectMapper,
                              RoomService roomService,
                              TeacherService teacherService,
                              FacultyService facultyService) {
        this.specialtyRepository = specialtyRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.groupRepository = groupRepository;
        this.roomRepository = roomRepository;
        this.resourceBundleUtil = resourceBundleUtil;
        this.subjectMapper = subjectMapper;
        this.roomService = roomService;
        this.teacherService = teacherService;
        this.facultyService = facultyService;
    }

    @Override
    @Transactional
    public boolean createSubject(CreateSubjectData subjectData, BindingResult bindingResult) {
        List<CreateCourseData> coursesData = subjectData.getCourses();
        if (coursesData != null && coursesData.size() > 0) {
            coursesData.remove(coursesData.size() - 1);
        }

        if (bindingResult.hasErrors()) {
            return false;
        }

        if (subjectRepository.existsByNameBgAndTypeAndArchivedFalse(
                subjectData.getNameBg(), subjectData.getType())) {
            throw createSubjectException("createSubject.nameAndTypeExistsValidation", subjectData);
        }

        Subject subject = subjectMapper.modelToEntity(subjectData);
        List<Course> courses = subject.getCourses();

        for (int i = 0; i < courses.size(); i++) {
            CreateCourseData courseData = coursesData.get(i);
            Course course = courses.get(i);

            Specialty specialty = specialtyRepository
                .findByIdAndArchivedFalse(courseData.getSpecialtyId())
                .orElseThrow(() -> createSubjectException("createCourse.specialtyNotFound", subjectData));

            Teacher teacher = teacherRepository
                .findByIdAndArchivedFalse(courseData.getTeacherId())
                .orElseThrow(() -> createSubjectException("createCourse.teacherNotFound", subjectData));

            Room room = roomRepository
                .findById(courseData.getRoomId())
                .orElseThrow(() -> createSubjectException("createCourse.roomNotFound", subjectData));

            course.setSpecialty(specialty);
            course.setTeacher(teacher);
            course.setRoom(room);
            course.setSubject(subject);

            if (courseData.getGroups() != null) {
                course.setGroups(
                    courseData.getGroups()
                        .stream().map(g -> groupRepository
                            .findAllByNameIgnoreCase(g)
                            .orElseGet(() -> Group.builder().name(g).build()))
                        .collect(Collectors.toList())
                );
            }
        }

        subjectRepository.save(subject);
        return true;
    }

    private ValidationException createSubjectException(String messageKey, CreateSubjectData subjectData) {
        String message = resourceBundleUtil.getMessage(messageKey);
        Map<String, Object> models = Map.of("createSubjectData", subjectData,
                "degrees", Degree.values(),
                "courseModes", CourseMode.values(),
                "facultyList", facultyService.getList(),
                "courseWeeks", CourseWeek.values(),
                "subjectTypes", SubjectType.values(),
                "courseYears", CourseYear.values(),
                "rooms", roomService.getList(),
                "teachers", teacherService.getList());
        return new ValidationException(message, "subject/create", models);
    }
}
