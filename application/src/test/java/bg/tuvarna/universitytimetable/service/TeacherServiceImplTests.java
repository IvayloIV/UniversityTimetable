package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.data.CreateTeacherData;
import bg.tuvarna.universitytimetable.entity.Teacher;
import bg.tuvarna.universitytimetable.entity.User;
import bg.tuvarna.universitytimetable.entity.enums.Role;
import bg.tuvarna.universitytimetable.mapper.TeacherMapper;
import bg.tuvarna.universitytimetable.repository.TeacherRepository;
import bg.tuvarna.universitytimetable.repository.UserRepository;
import bg.tuvarna.universitytimetable.service.impl.TeacherServiceImpl;
import bg.tuvarna.universitytimetable.util.ResourceBundleUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeacherServiceImplTests {

    private TeacherRepository teacherRepository;
    private UserRepository userRepository;
    private ResourceBundleUtil resourceBundleUtil;
    private TeacherMapper teacherMapper;
    private TeacherService teacherService;

    @BeforeEach
    public void init() {
        teacherRepository = mock(TeacherRepository.class);
        userRepository = mock(UserRepository.class);
        resourceBundleUtil = mock(ResourceBundleUtil.class);
        teacherMapper = mock(TeacherMapper.class);

        teacherService =
                new TeacherServiceImpl(
                teacherRepository,
                userRepository,
                resourceBundleUtil,
                teacherMapper);
    }

    @Test
    public void testCreate_WithValidInput_ExpectSuccessfulOperation() {
        String email = "ivanov@gmail.com";
        String ucn = "9102041573";
        String academicRankBg = "проф.";
        String academicRankEn = "prof.";
        String firstNameBg = "Николай";
        String firstNameEn = "Nikolay";
        String lastNameBg = "Георгиев";
        String lastNameEn = "Georgiev";

        CreateTeacherData createTeacherData = new CreateTeacherData();
        createTeacherData.setEmail(email);
        createTeacherData.setUcn(ucn);
        createTeacherData.setAcademicRankBg(academicRankBg);
        createTeacherData.setAcademicRankEn(academicRankEn);
        createTeacherData.setFirstNameBg(firstNameBg);
        createTeacherData.setFirstNameEn(firstNameEn);
        createTeacherData.setLastNameBg(lastNameBg);
        createTeacherData.setLastNameEn(lastNameEn);
        createTeacherData.setTeacherFreeTime(List.of());

        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword("1234");
        user.setRole(Role.TEACHER);
        user.setCreatedDate(LocalDateTime.now());

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setAcademicRankBg(academicRankBg);
        teacher.setAcademicRankEn(academicRankEn);
        teacher.setFirstNameBg(firstNameBg);
        teacher.setFirstNameEn(firstNameEn);
        teacher.setLastNameBg(lastNameBg);
        teacher.setLastNameEn(lastNameEn);
        teacher.setArchived(false);
        teacher.setUser(user);
        teacher.setTeacherFreeTime(List.of());

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(teacherRepository.existsByUcnAndArchivedFalse(email)).thenReturn(false);
        when(teacherMapper.modelToEntity(createTeacherData)).thenReturn(teacher);

        boolean teacherCreated = teacherService.createTeacher(createTeacherData, bindingResult);
        assertTrue(teacherCreated);

        ArgumentCaptor<Teacher> teacherCaptor = ArgumentCaptor.forClass(Teacher.class);
        verify(teacherRepository, times(1)).save(teacherCaptor.capture());

        Teacher teacherCaptorValue = teacherCaptor.getValue();
        assertNotNull(teacherCaptorValue);
        assertThat(teacherCaptorValue).usingRecursiveComparison().isEqualTo(teacher);
    }
}
