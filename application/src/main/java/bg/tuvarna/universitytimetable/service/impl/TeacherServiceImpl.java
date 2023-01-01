package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.dto.data.CreateTeacherData;
import bg.tuvarna.universitytimetable.dto.data.TeacherFreeTimeData;
import bg.tuvarna.universitytimetable.dto.model.TeacherListModel;
import bg.tuvarna.universitytimetable.dto.model.TeacherScheduleFilterModel;
import bg.tuvarna.universitytimetable.entity.Teacher;
import bg.tuvarna.universitytimetable.exception.EntityNotFoundException;
import bg.tuvarna.universitytimetable.exception.ValidationException;
import bg.tuvarna.universitytimetable.mapper.TeacherMapper;
import bg.tuvarna.universitytimetable.repository.TeacherRepository;
import bg.tuvarna.universitytimetable.repository.UserRepository;
import bg.tuvarna.universitytimetable.service.TeacherService;
import bg.tuvarna.universitytimetable.util.DayOfWeekUtil;
import bg.tuvarna.universitytimetable.util.ResourceBundleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final ResourceBundleUtil resourceBundleUtil;
    private final TeacherMapper teacherMapper;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              UserRepository userRepository,
                              ResourceBundleUtil resourceBundleUtil,
                              TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.resourceBundleUtil = resourceBundleUtil;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public boolean createTeacher(CreateTeacherData createTeacherData, BindingResult bindingResult) {
        List<TeacherFreeTimeData> teacherFreeTime = createTeacherData.getTeacherFreeTime();
        if (teacherFreeTime != null && teacherFreeTime.size() > 0) {
            teacherFreeTime.remove(teacherFreeTime.size() - 1);
        }

        if (bindingResult.hasErrors()) {
            return false;
        }

        if (userRepository.findByEmail(createTeacherData.getEmail()).isPresent()) {
            throwCreateTeacherException("createTeacher.emailExistsValidation", createTeacherData);
        }

        if (teacherRepository.existsByUcnAndArchivedFalse(createTeacherData.getUcn())) {
            throwCreateTeacherException("createTeacher.ucnExistsValidation", createTeacherData);
        }

        teacherFreeTime.stream().collect(Collectors.groupingBy(TeacherFreeTimeData::getDay, Collectors.toList()))
            .forEach((d, s) -> {
                s.sort(Comparator.comparing(TeacherFreeTimeData::getStartTime));
                for (int i = 0; i < s.size() - 1; i++) {
                    TeacherFreeTimeData currTime = s.get(i);
                    TeacherFreeTimeData nextTime = s.get(i + 1);
                    if (!(currTime.getStartTime().compareTo(nextTime.getEndTime()) > 0 ||
                            currTime.getEndTime().compareTo(nextTime.getStartTime()) < 0)) {
                        throwCreateTeacherException("createTeacher.freeTimeRangeValidation", createTeacherData);
                    }
                }
            });

        Teacher teacher = teacherMapper.modelToEntity(createTeacherData);
        teacher.getTeacherFreeTime().forEach(tft -> tft.setTeacher(teacher));
        teacherRepository.save(teacher);
        return true;
    }

    @Override
    public List<TeacherListModel> getList() {
        List<Teacher> teachers = teacherRepository.findAllByArchivedFalse();
        return teacherMapper.entityToModel(teachers);
    }

    @Override
    public void delete(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
            .orElseThrow(() -> new EntityNotFoundException(resourceBundleUtil.getMessage("teacherList.notFound")));
        teacher.setArchived(true);
        teacherRepository.save(teacher);
    }

    @Override
    public TeacherScheduleFilterModel getFilterModelById(Long id) {
        Teacher teacher = teacherRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(resourceBundleUtil.getMessage("teacherSchedule.teacherNotFound")));
        return teacherMapper.entityToFilterModel(teacher);
    }

    @Override
    public List<TeacherScheduleFilterModel> getFilterModels() {
        List<Teacher> teachers = teacherRepository.findAllByArchivedFalse();
        return teacherMapper.entityToFilterModel(teachers);
    }

    @Override
    public Teacher getById(Long id) {
        return teacherRepository.findByIdAndArchivedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(resourceBundleUtil.getMessage("teacherSchedule.teacherNotFound")));
    }

    private void throwCreateTeacherException(String messageKey, CreateTeacherData createTeacherData) {
        String message = resourceBundleUtil.getMessage(messageKey);
        Map<String, Object> models = Map.of("createTeacherData", createTeacherData,
                "dayOfWeek", DayOfWeekUtil.getLocaleDays(),
                "teacherListModel", getList());
        throw new ValidationException(message, "teacher/create", models);
    }
}
