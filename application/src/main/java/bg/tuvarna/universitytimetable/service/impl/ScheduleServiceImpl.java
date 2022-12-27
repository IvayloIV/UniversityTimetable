package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.dto.data.ScheduleEditData;
import bg.tuvarna.universitytimetable.dto.data.StudentScheduleSearchData;
import bg.tuvarna.universitytimetable.dto.model.*;
import bg.tuvarna.universitytimetable.entity.Course;
import bg.tuvarna.universitytimetable.entity.Faculty;
import bg.tuvarna.universitytimetable.entity.Schedule;
import bg.tuvarna.universitytimetable.entity.enums.*;
import bg.tuvarna.universitytimetable.exception.ValidationException;
import bg.tuvarna.universitytimetable.mapper.GroupMapper;
import bg.tuvarna.universitytimetable.mapper.ScheduleMapper;
import bg.tuvarna.universitytimetable.repository.ScheduleRepository;
import bg.tuvarna.universitytimetable.service.AcademicYearService;
import bg.tuvarna.universitytimetable.service.FacultyService;
import bg.tuvarna.universitytimetable.service.ScheduleService;
import bg.tuvarna.universitytimetable.util.ResourceBundleUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

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
    private final GroupMapper groupMapper;
    private final FacultyService facultyService;
    private final AcademicYearService academicYearService;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository,
                               ResourceBundleUtil resourceBundleUtil,
                               ScheduleMapper scheduleMapper,
                               GroupMapper groupMapper,
                               FacultyService facultyService,
                               AcademicYearService academicYearService) {
        this.scheduleRepository = scheduleRepository;
        this.resourceBundleUtil = resourceBundleUtil;
        this.scheduleMapper = scheduleMapper;
        this.groupMapper = groupMapper;
        this.facultyService = facultyService;
        this.academicYearService = academicYearService;
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
            throw new ValidationException(message, "schedule/generate", Map.of("daysOfWeek", getDaysOfWeek()));
        }

        scheduleRepository.updateStatus(ScheduleStatus.ACTIVE, ScheduleStatus.INACTIVE);
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
                 !currCourse.getRoom().getId().equals(course.getRoom().getId()))) {
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
        boolean fieldsNotEmpty = Arrays.stream(StudentScheduleSearchData.class.getDeclaredFields())
                .allMatch(s -> {
                    s.setAccessible(true);
                    try {
                        return s.get(studentScheduleSearchData) != null;
                    } catch (IllegalAccessException e) {
                        log.error(e.getMessage());
                        return false;
                    }
                });

        if (fieldsNotEmpty) {
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

                modelAndView.addObject("scheduleCourse", courseModel);
                modelAndView.addObject("disableEdit", true);
            }
        }

        modelAndView.addObject("faculties", facultyService.getList());
        modelAndView.addObject("years", academicYearService.getYears());
        modelAndView.addObject("semesters", Semester.values());
        modelAndView.addObject("degrees", Degree.values());
        modelAndView.addObject("courseYears", CourseYear.values());
        modelAndView.addObject("courseModes", CourseMode.values());
    }

    private Schedule findById(Long id) {
        return scheduleRepository.findById(id) //TODO: check if home page need some addition models
            .orElseThrow(() -> new ValidationException(resourceBundleUtil.getMessage("scheduleEdit.notFound"), "/"));
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
                Map.of("daysOfWeek", getDaysOfWeek(),
                        "schedule", scheduleEditModel));
    }

    private List<DayOfWeek> getDaysOfWeek() {
        return List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
    }
}
