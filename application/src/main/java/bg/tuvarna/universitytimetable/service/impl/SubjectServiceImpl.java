package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.dto.data.CreateCourseData;
import bg.tuvarna.universitytimetable.dto.data.CreateSubjectData;
import bg.tuvarna.universitytimetable.dto.data.SubjectSearchData;
import bg.tuvarna.universitytimetable.dto.model.SubjectPaginatedModel;
import bg.tuvarna.universitytimetable.entity.*;
import bg.tuvarna.universitytimetable.entity.enums.*;
import bg.tuvarna.universitytimetable.exception.ValidationException;
import bg.tuvarna.universitytimetable.mapper.SubjectMapper;
import bg.tuvarna.universitytimetable.repository.*;
import bg.tuvarna.universitytimetable.service.*;
import bg.tuvarna.universitytimetable.util.DayOfWeekUtil;
import bg.tuvarna.universitytimetable.util.ResourceBundleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static bg.tuvarna.universitytimetable.repository.specification.SubjectSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final static Integer SUBJECTS_PER_PAGE = 16;

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
    private final CourseService courseService;

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
                              FacultyService facultyService,
                              CourseService courseService) {
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
        this.courseService = courseService;
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
                        .stream().map(g -> groupRepository.findAllByNameIgnoreCase(g)
                            .orElseGet(() -> groupRepository.save(Group.builder().name(g).build())))
                        .collect(Collectors.toList())
                );
            }

            if (course.getCourseTimes() != null) {
                course.getCourseTimes().forEach(ct -> ct.setCourse(course));
            }
        }

        subjectRepository.save(subject);
        return true;
    }

    @Override
    @Transactional
    public SubjectPaginatedModel getList(SubjectSearchData searchData) {
        Pageable pageable = PageRequest.of(searchData.getPage() - 1, SUBJECTS_PER_PAGE,
                Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<Subject> subjectsPage = subjectRepository.findAll(
                where(withName(searchData.getName()))
                        .and(withType(searchData.getSubjectType()))
                        .and(withFacultyId(searchData.getFacultyId()))
                        .and(withDepartmentId(searchData.getDepartmentId()))
                        .and(withSpecialtyId(searchData.getSpecialtyId()))
                        .and(withTeacherId(searchData.getTeacherId()))
                        .and(isArchived(false)),
                pageable
        );

        List<Subject> subjects = subjectsPage.getContent();
        subjects.forEach(s -> s.setCourses(courseService.getCoursesBySubjectId(s.getId())));

        return SubjectPaginatedModel.builder()
                .currentPage(subjectsPage.getNumber() + 1)
                .totalPages(subjectsPage.getTotalPages())
                .subjects(subjectMapper.entityToModel(subjects))
                .build();
    }

    @Override
    public String updateActiveStatus(Long id) {
        Subject subject = findById(id);
        subject.setActive(!subject.getActive());
        subjectRepository.save(subject);
        return resourceBundleUtil.getMessage("courseList.statusUpdate");
    }

    @Override
    public void archiveSubject(Long id) {
        Subject subject = findById(id);
        subject.setArchived(true);
        subjectRepository.save(subject);
    }

    private Subject findById(Long id) {
        return subjectRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(resourceBundleUtil.getMessage("courseList.subjectNotFound")));
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
                "teachers", teacherService.getList(),
                "dayOfWeek", DayOfWeekUtil.getLocaleDays());
        return new ValidationException(message, "subject/create", models);
    }
}
