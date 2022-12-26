package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.dto.model.CourseScheduleModel;
import bg.tuvarna.universitytimetable.dto.model.FacultyScheduleModel;
import bg.tuvarna.universitytimetable.dto.model.GroupScheduleModel;
import bg.tuvarna.universitytimetable.dto.model.ScheduleDetailsModel;
import bg.tuvarna.universitytimetable.entity.Faculty;
import bg.tuvarna.universitytimetable.entity.Schedule;
import bg.tuvarna.universitytimetable.entity.enums.ScheduleStatus;
import bg.tuvarna.universitytimetable.exception.ValidationException;
import bg.tuvarna.universitytimetable.mapper.GroupMapper;
import bg.tuvarna.universitytimetable.mapper.ScheduleMapper;
import bg.tuvarna.universitytimetable.repository.ScheduleRepository;
import bg.tuvarna.universitytimetable.service.ScheduleService;
import bg.tuvarna.universitytimetable.util.ResourceBundleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ResourceBundleUtil resourceBundleUtil;
    private final ScheduleMapper scheduleMapper;
    private final GroupMapper groupMapper;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository,
                               ResourceBundleUtil resourceBundleUtil,
                               ScheduleMapper scheduleMapper,
                               GroupMapper groupMapper) {
        this.scheduleRepository = scheduleRepository;
        this.resourceBundleUtil = resourceBundleUtil;
        this.scheduleMapper = scheduleMapper;
        this.groupMapper = groupMapper;
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

            if (scheduleModel.getGroup() != null) {
                SortedSet<GroupScheduleModel> groupModelsSet = courseModel.getGroups();
                if (groupModelsSet.size() == 1 && groupModelsSet.first().getId().equals(-1L)) {
                    groupModelsSet.removeIf(g -> g.getId().equals(-1L));
                }
                groupModelsSet.add(scheduleModel.getGroup());
            }

            addNewSchedule(scheduleModel, courseModel);
        }

        return facultyModels;
    }

    @Override
    @Transactional
    public void save() {
        if (scheduleRepository.countByStatus(ScheduleStatus.PENDING) == 0) {
            String message = resourceBundleUtil.getMessage("scheduleList.timetablesNotFound");
            List<DayOfWeek> daysOfWeek = List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
            throw new ValidationException(message, "schedule/generate", Map.of("daysOfWeek", daysOfWeek));
        }

        scheduleRepository.updateStatus(ScheduleStatus.ACTIVE, ScheduleStatus.INACTIVE);
        scheduleRepository.updateStatus(ScheduleStatus.PENDING, ScheduleStatus.ACTIVE);
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
}
