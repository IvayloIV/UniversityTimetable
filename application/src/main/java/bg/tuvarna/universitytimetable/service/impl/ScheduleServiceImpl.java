package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.dto.data.ScheduleEditData;
import bg.tuvarna.universitytimetable.dto.data.StudentScheduleSearchData;
import bg.tuvarna.universitytimetable.dto.data.TeacherScheduleSearchData;
import bg.tuvarna.universitytimetable.dto.model.*;
import bg.tuvarna.universitytimetable.entity.*;
import bg.tuvarna.universitytimetable.entity.enums.*;
import bg.tuvarna.universitytimetable.exception.EntityNotFoundException;
import bg.tuvarna.universitytimetable.exception.ValidationException;
import bg.tuvarna.universitytimetable.mapper.ScheduleMapper;
import bg.tuvarna.universitytimetable.repository.ScheduleRepository;
import bg.tuvarna.universitytimetable.service.*;
import bg.tuvarna.universitytimetable.util.DayOfWeekUtil;
import bg.tuvarna.universitytimetable.util.QueryParamsUtil;
import bg.tuvarna.universitytimetable.util.ResourceBundleUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static bg.tuvarna.universitytimetable.repository.specification.ScheduleSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ResourceBundleUtil resourceBundleUtil;
    private final ScheduleMapper scheduleMapper;
    private final FacultyService facultyService;
    private final AcademicYearService academicYearService;
    private final PdfService pdfService;
    private final CsvParserService csvParserService;
    private final EmailService emailService;
    private final QueryParamsUtil queryParamsUtil;
    private final TeacherService teacherService;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository,
                               ResourceBundleUtil resourceBundleUtil,
                               ScheduleMapper scheduleMapper,
                               FacultyService facultyService,
                               AcademicYearService academicYearService,
                               PdfService pdfService,
                               CsvParserService csvParserService,
                               EmailService emailService,
                               QueryParamsUtil queryParamsUtil,
                               TeacherService teacherService) {
        this.scheduleRepository = scheduleRepository;
        this.resourceBundleUtil = resourceBundleUtil;
        this.scheduleMapper = scheduleMapper;
        this.facultyService = facultyService;
        this.academicYearService = academicYearService;
        this.pdfService = pdfService;
        this.csvParserService = csvParserService;
        this.emailService = emailService;
        this.queryParamsUtil = queryParamsUtil;
        this.teacherService = teacherService;
    }

    @Override
    @Transactional
    public List<FacultyScheduleModel> findAll() {
        Sort sort = Sort.by(List.of(
            new Order(Sort.Direction.ASC, "course.specialty.department.faculty.nameBg"),
            new Order(Sort.Direction.ASC, "academicYear.year"),
            new Order(Sort.Direction.ASC, "academicYear.semester"),
            new Order(Sort.Direction.ASC, "course.specialty.department.nameBg"),
            new Order(Sort.Direction.ASC, "course.specialty.nameBg"),
            new Order(Sort.Direction.ASC, "course.degree"),
            new Order(Sort.Direction.ASC, "course.year"),
            new Order(Sort.Direction.ASC, "course.mode")
        ));

        List<Schedule> schedules = scheduleRepository.findAllByStatus(ScheduleStatus.PENDING, sort);
        List<FacultyScheduleModel> facultyModels = new ArrayList<>();

        for (Schedule schedule : schedules) {
            Faculty faculty = schedule.getCourse().getSpecialty().getDepartment().getFaculty();
            FacultyScheduleModel facultyModel;

            if (facultyModels.size() == 0 ||
                    !facultyModels.get(facultyModels.size() - 1).getNameBg().equals(faculty.getNameBg())) {
                facultyModel = new FacultyScheduleModel();
                facultyModel.setNameBg(faculty.getNameBg());
                facultyModel.setCourses(new ArrayList<>());
                facultyModels.add(facultyModel);
            } else {
                facultyModel = facultyModels.get(facultyModels.size() - 1);
            }

            List<CourseScheduleModel> courses = facultyModel.getCourses();
            CourseScheduleModel courseModel = scheduleMapper.entityToCourseModel(schedule);
            if (courses.size() == 0 || !courses.get(courses.size() - 1).equals(courseModel)) {
                courseModel.setGroups(new TreeSet<>());
                addDefaultGroup(courseModel);
                courseModel.setSchedules(new HashMap<>());
                courses.add(courseModel);
            } else {
                courseModel = courses.get(courses.size() - 1);
            }

            ScheduleDetailsModel scheduleModel = scheduleMapper.entityToScheduleModel(schedule);
            addNewGroup(scheduleModel, courseModel);
            addNewSchedule(scheduleModel, courseModel);
        }

        return facultyModels;
    }

    @Override
    @Transactional
    public void save() {
        if (scheduleRepository.countByStatus(ScheduleStatus.PENDING) == 0) {
            String message = resourceBundleUtil.getMessage("scheduleList.timetablesNotFound");
            throw new ValidationException(message, "schedule/generate", Map.of("daysOfWeek", DayOfWeekUtil.getWorkDays()));
        }

        AcademicYear lastAcademicYear = academicYearService.getLastAcademicYear();
        scheduleRepository.updateStatusByYearId(ScheduleStatus.ACTIVE, ScheduleStatus.INACTIVE, lastAcademicYear.getId());
        scheduleRepository.updateStatus(ScheduleStatus.PENDING, ScheduleStatus.ACTIVE);
    }

    @Override
    public ScheduleEditModel getEditModel(Long id) {
        return scheduleMapper.entityToEditModel(findById(id));
    }

    @Override
    public void edit(Long id, ScheduleEditData scheduleEditData) {
        Schedule schedule = findById(id);
        LocalTime startTime = scheduleEditData.getStartTime();
        LocalTime endTime = scheduleEditData.getEndTime();

        if (!schedule.getStatus().equals(ScheduleStatus.PENDING)) {
            throwEditScheduleException("scheduleEdit.notAllowed", schedule, scheduleEditData);
        }

        if (startTime.compareTo(LocalTime.of(7, 15)) < 0
                || startTime.compareTo(LocalTime.of(20, 0)) > 0) {
            throwEditScheduleException("scheduleEdit.startTimeIntervalValidation", schedule, scheduleEditData);
        }

        if (endTime.compareTo(LocalTime.of(7, 15)) < 0
                || endTime.compareTo(LocalTime.of(20, 0)) > 0) {
            throwEditScheduleException("scheduleEdit.endTimeIntervalValidation", schedule, scheduleEditData);
        }

        if (startTime.getMinute() % 15 != 0) {
            throwEditScheduleException("scheduleEdit.startTimeMinutesValidation", schedule, scheduleEditData);
        }

        if (endTime.getMinute() % 15 != 0) {
            throwEditScheduleException("scheduleEdit.endTimeMinutesValidation", schedule, scheduleEditData);
        }

        if (startTime.compareTo(endTime) >= 0) {
            throwEditScheduleException("scheduleEdit.endTimeBeforeTheStartValidation", schedule, scheduleEditData);
        }

        List<Schedule> schedules = scheduleRepository.findAll(
            where(withStatuses(List.of(ScheduleStatus.PENDING)))
                .and(withDay(DayOfWeek.of(scheduleEditData.getDay())))
                .and(withStartTimeBefore(startTime))
                .and(withEndTimeAfter(endTime))
        );

        for (Schedule s : schedules) {
            if (s.getId().equals(schedule.getId())) {
                continue;
            }

            Course currCourse = s.getCourse();
            Course course = schedule.getCourse();

            if (currCourse.getTeacher().getId().equals(course.getTeacher().getId()) &&
                (currCourse.getWeek().equals(CourseWeek.ALL) || course.getWeek().equals(CourseWeek.ALL) || currCourse.getWeek().equals(course.getWeek())) &&
                (!currCourse.getSubject().getId().equals(course.getSubject().getId()) ||
                 !currCourse.getRoom().getId().equals(course.getRoom().getId()) ||
                 !s.getStartTime().equals(scheduleEditData.getStartTime()) ||
                 !s.getEndTime().equals(scheduleEditData.getEndTime()))) {
                throwEditScheduleException("scheduleEdit.busyTeacherValidation", schedule, scheduleEditData);
            }

            if (currCourse.getRoom().getId().equals(course.getRoom().getId()) &&
                (currCourse.getWeek().equals(CourseWeek.ALL) || course.getWeek().equals(CourseWeek.ALL) || currCourse.getWeek().equals(course.getWeek())) &&
                (!currCourse.getSubject().getId().equals(course.getSubject().getId()) ||
                 !currCourse.getTeacher().getId().equals(course.getTeacher().getId()))) {
                throwEditScheduleException("scheduleEdit.occupiedRoomValidation", schedule, scheduleEditData);
            }

            if (currCourse.getSpecialty().getId().equals(course.getSpecialty().getId()) &&
                currCourse.getDegree().equals(course.getDegree()) &&
                currCourse.getYear().equals(course.getYear()) &&
                currCourse.getMode().equals(course.getMode()) &&
                (s.getGroup() == null || schedule.getGroup() == null ||
                    s.getGroup().getId().equals(schedule.getGroup().getId()))) {
                throwEditScheduleException("scheduleEdit.classOverlapValidation", schedule, scheduleEditData);
            }
        }

        scheduleMapper.updateSchedule(scheduleEditData, schedule);
        scheduleRepository.save(schedule);
    }

    @Override
    public void loadStudentSchedule(StudentScheduleSearchData studentScheduleSearchData, ModelAndView modelAndView) {
        CourseScheduleModel courseModel = getCourseScheduleModel(studentScheduleSearchData);

        if (courseModel != null) {
            modelAndView.addObject("scheduleCourse", courseModel);
            modelAndView.addObject("disableEdit", true);
        }

        modelAndView.addObject("faculties", facultyService.getList());
        modelAndView.addObject("years", academicYearService.getYears());
        modelAndView.addObject("semesters", Semester.values());
        modelAndView.addObject("degrees", Degree.values());
        modelAndView.addObject("courseYears", CourseYear.values());
        modelAndView.addObject("courseModes", CourseMode.values());
    }

    @Override
    public ResponseEntity<Resource> generateStudentSchedule(StudentScheduleSearchData studentScheduleSearchData) {
        CourseScheduleModel courseModel = getCourseScheduleModel(studentScheduleSearchData);
        Resource resource = pdfService.generateStudentSchedule(courseModel);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=timetable.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @Override
    public void notifyStudents(MultipartFile studentsNameCsv, StudentScheduleSearchData searchData, RedirectAttributes attributes) throws IOException {
        String messageKey;

        if (studentsNameCsv.isEmpty()) {
            messageKey = "studentSchedule.fileNotFound";
        } else {
            List<String> emails = csvParserService.getEmails(studentsNameCsv);
            CourseScheduleModel courseScheduleModel = getCourseScheduleModel(searchData);
            Resource resource = pdfService.generateStudentSchedule(courseScheduleModel);
            emailService.sendScheduleNotifications(courseScheduleModel.getDegree().getLanguage(), emails, resource);
            messageKey = "studentSchedule.sentMails";
        }
        attributes.addFlashAttribute("message", resourceBundleUtil.getMessage(messageKey));
        queryParamsUtil.attachQueryParams(attributes, searchData);
    }

    @Override
    public void loadTeacherSchedule(TeacherScheduleSearchData teacherScheduleSearchData, ModelAndView modelAndView) {
        if (isFieldsNotEmpty(teacherScheduleSearchData)) {
            Map<String, List<TeacherScheduleModel>> scheduleMap = getTeacherScheduleModel(teacherScheduleSearchData);

            if (scheduleMap.size() == 0) {
                modelAndView.addObject("message", resourceBundleUtil.getMessage("teacherSchedule.scheduleNotFound"));
            } else {
                modelAndView.addObject("academicYear", teacherScheduleSearchData.getAcademicYear());
                modelAndView.addObject("semester", teacherScheduleSearchData.getSemester());
                modelAndView.addObject("teacher", teacherService.getFilterModelById(teacherScheduleSearchData.getTeacherId()));
                modelAndView.addObject("courseWeeks", List.of(CourseWeek.EVEN, CourseWeek.ODD));
                modelAndView.addObject("schedules", scheduleMap);
            }
        }

        modelAndView.addObject("years", academicYearService.getYears());
        modelAndView.addObject("semesters", Semester.values());
        modelAndView.addObject("teachers", teacherService.getFilterModels());
    }

    @Override
    public ResponseEntity<Resource> generateTeacherSchedule(TeacherScheduleSearchData teacherScheduleSearchData) {
        Map<String, List<TeacherScheduleModel>> scheduleMap = getTeacherScheduleModel(teacherScheduleSearchData);
        Resource resource = pdfService.generateTeacherSchedule(teacherScheduleSearchData, scheduleMap);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=timetable.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @Override
    public void notifyTeacher(TeacherScheduleSearchData teacherScheduleSearchData, RedirectAttributes attributes) throws IOException {
        Map<String, List<TeacherScheduleModel>> scheduleModel = getTeacherScheduleModel(teacherScheduleSearchData);
        Resource resource = pdfService.generateTeacherSchedule(teacherScheduleSearchData, scheduleModel);
        Teacher teacher = teacherService.getById(teacherScheduleSearchData.getTeacherId());
        emailService.sendScheduleNotifications("bg", List.of(teacher.getUser().getEmail()), resource);

        attributes.addFlashAttribute("message", resourceBundleUtil.getMessage("teacherSchedule.sentMail"));
        queryParamsUtil.attachQueryParams(attributes, teacherScheduleSearchData);
    }

    private Map<String, List<TeacherScheduleModel>> getTeacherScheduleModel(TeacherScheduleSearchData teacherScheduleSearchData) {
        String[] academicYears = teacherScheduleSearchData.getAcademicYear().split("/");
        Short academicYear = Short.parseShort(academicYears[teacherScheduleSearchData.getSemester().equals(Semester.WINTER) ? 0 : 1]);

        List<Schedule> schedules = scheduleRepository.findAll(
                where(withStatuses(List.of(ScheduleStatus.ACTIVE))
                        .and(withAcademicYear(academicYear))
                        .and(withSemester(teacherScheduleSearchData.getSemester()))
                        .and(withTeacherId(teacherScheduleSearchData.getTeacherId()))
                )
        );

        Map<String, List<TeacherScheduleModel>> scheduleMap = new HashMap<>();
        for (Schedule schedule : schedules) {
            List<TeacherScheduleModel> teacherScheduleModels;
            String startTimeFormatted = schedule.getStartTime().format(DateTimeFormatter.ofPattern("H:m"));

            if (scheduleMap.containsKey(startTimeFormatted)) {
                teacherScheduleModels = scheduleMap.get(startTimeFormatted);
            } else {
                teacherScheduleModels = new ArrayList<>();
                scheduleMap.put(startTimeFormatted, teacherScheduleModels);
            }

            Set<TeacherCourseModel> teacherCourseModels = null;
            TeacherScheduleModel teacherScheduleModel = scheduleMapper.entityToTeacherScheduleModel(schedule);
            for (TeacherScheduleModel currTeacherScheduleModel : teacherScheduleModels) {
                if (currTeacherScheduleModel.equals(teacherScheduleModel)) {
                    if (teacherScheduleModel.getWeek().equals(CourseWeek.ALL)) {
                        currTeacherScheduleModel.setWeek(CourseWeek.ALL);
                    }
                    teacherCourseModels = currTeacherScheduleModel.getCourses();
                    break;
                }
            }

            if (teacherCourseModels == null) {
                teacherScheduleModels.add(teacherScheduleModel);
                teacherCourseModels = new HashSet<>();
                teacherScheduleModel.setCourses(teacherCourseModels);
            }

            SortedSet<String> groups;
            TeacherCourseModel teacherCourseModel = scheduleMapper.entityToTeacherCourseModel(schedule);
            if (!teacherCourseModels.contains(teacherCourseModel)) {
                teacherCourseModels.add(teacherCourseModel);
                groups = new TreeSet<>();
                teacherCourseModel.setGroups(groups);
            } else {
                groups = teacherCourseModels.stream()
                        .filter(c -> c.equals(teacherCourseModel))
                        .findFirst()
                        .get()
                        .getGroups();
            }

            Group group = schedule.getGroup();
            if (group != null) {
                groups.add(group.getName());
            }
        }

        return scheduleMap;
    }

    private CourseScheduleModel getCourseScheduleModel(StudentScheduleSearchData studentScheduleSearchData) {
        if (isFieldsNotEmpty(studentScheduleSearchData)) {
            String[] academicYears = studentScheduleSearchData.getAcademicYear().split("/");
            Short academicYear = Short.parseShort(academicYears[studentScheduleSearchData.getSemester().equals(Semester.WINTER) ? 0 : 1]);

            List<Schedule> schedules = scheduleRepository.findAll(
                    where(withStatuses(List.of(ScheduleStatus.ACTIVE, ScheduleStatus.INACTIVE))
                            .and(withAcademicYear(academicYear))
                            .and(withSemester(studentScheduleSearchData.getSemester()))
                            .and(withDegree(studentScheduleSearchData.getDegree()))
                            .and(withSpecialtyId(studentScheduleSearchData.getSpecialtyId()))
                            .and(withCourseYear(studentScheduleSearchData.getCourseYear()))
                            .and(withCourseMode(studentScheduleSearchData.getMode()))
                    )
            );

            if (schedules.size() > 0) {
                CourseScheduleModel courseModel = scheduleMapper.entityToCourseModel(schedules.get(0));
                courseModel.setGroups(new TreeSet<>());
                addDefaultGroup(courseModel);
                courseModel.setSchedules(new HashMap<>());

                schedules.forEach(s -> {
                    ScheduleDetailsModel scheduleModel = scheduleMapper.entityToScheduleModel(s);
                    addNewGroup(scheduleModel, courseModel);
                    addNewSchedule(scheduleModel, courseModel);
                });

                return courseModel;
            }
        }

        return null;
    }

    private static boolean isFieldsNotEmpty(Object object) {
        boolean fieldsNotEmpty = Arrays.stream(object.getClass().getDeclaredFields())
                .allMatch(s -> {
                    s.setAccessible(true);
                    try {
                        return s.get(object) != null;
                    } catch (IllegalAccessException e) {
                        log.error(e.getMessage());
                        return false;
                    }
                });
        return fieldsNotEmpty;
    }

    private Schedule findById(Long id) {
        return scheduleRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(resourceBundleUtil.getMessage("scheduleEdit.notFound")));
    }

    private void addNewSchedule(ScheduleDetailsModel scheduleModel, CourseScheduleModel courseModel) {
        String time = scheduleModel.getStartTime().format(DateTimeFormatter.ofPattern("H:m"));

        Map<String, List<ScheduleDetailsModel>> schedulesMap = courseModel.getSchedules();
        if (!schedulesMap.containsKey(time)) {
            schedulesMap.put(time, new ArrayList<>());
        }
        schedulesMap.get(time).add(scheduleModel);
    }

    private void addDefaultGroup(CourseScheduleModel courseModel) {
        GroupScheduleModel defaultGroup = new GroupScheduleModel();
        defaultGroup.setId(-1L);
        defaultGroup.setName("");
        courseModel.getGroups().add(defaultGroup);
    }

    private void addNewGroup(ScheduleDetailsModel scheduleModel, CourseScheduleModel courseModel) {
        if (scheduleModel.getGroup() != null) {
            SortedSet<GroupScheduleModel> groupModelsSet = courseModel.getGroups();
            if (groupModelsSet.size() == 1 && groupModelsSet.first().getId().equals(-1L)) {
                groupModelsSet.removeIf(g -> g.getId().equals(-1L));
            }
            groupModelsSet.add(scheduleModel.getGroup());
        }
    }

    private void throwEditScheduleException(String messageKey, Schedule schedule, ScheduleEditData scheduleEditData) {
        String message = resourceBundleUtil.getMessage(messageKey);
        ScheduleEditModel scheduleEditModel = scheduleMapper.entityToEditModel(schedule);
        scheduleMapper.updateScheduleModel(scheduleEditData, scheduleEditModel);

        throw new ValidationException(message, "schedule/edit",
                Map.of("daysOfWeek", DayOfWeekUtil.getWorkDays(),
                        "schedule", scheduleEditModel));
    }
}
