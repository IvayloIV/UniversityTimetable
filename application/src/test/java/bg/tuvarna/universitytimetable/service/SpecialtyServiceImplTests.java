package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.entity.Specialty;
import bg.tuvarna.universitytimetable.mapper.SpecialtyMapper;
import bg.tuvarna.universitytimetable.repository.DepartmentRepository;
import bg.tuvarna.universitytimetable.repository.SpecialtyRepository;
import bg.tuvarna.universitytimetable.service.impl.SpecialtyServiceImpl;
import bg.tuvarna.universitytimetable.util.ResourceBundleUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class SpecialtyServiceImplTests {

    private SpecialtyRepository specialtyRepository;
    private SpecialtyMapper specialtyMapper;
    private DepartmentRepository departmentRepository;
    private FacultyService facultyService;
    private ResourceBundleUtil resourceBundleUtil;
    private SpecialtyService specialtyService;

    @BeforeEach
    public void init() {
        specialtyRepository = mock(SpecialtyRepository.class);
        specialtyMapper = mock(SpecialtyMapper.class);
        departmentRepository = mock(DepartmentRepository.class);
        facultyService = mock(FacultyService.class);
        resourceBundleUtil = mock(ResourceBundleUtil.class);

        specialtyService =
                new SpecialtyServiceImpl(
                specialtyRepository,
                specialtyMapper,
                departmentRepository,
                facultyService,
                resourceBundleUtil);
    }

    @Test
    public void testDelete_WithValidInput_ExpectSuccessfulOperation() {
        Long specialtyId = 1L;

        Specialty specialty = new Specialty();
        specialty.setId(specialtyId);
        specialty.setNameBg("СИ");
        specialty.setNameEn("SE");
        specialty.setArchived(false);
        specialty.setCreatedDate(LocalDateTime.now());

        when(specialtyRepository.findById(specialtyId)).thenReturn(Optional.of(specialty));

        specialtyService.delete(specialtyId);

        ArgumentCaptor<Specialty> specialtyCaptor = ArgumentCaptor.forClass(Specialty.class);
        verify(specialtyRepository, times(1)).save(specialtyCaptor.capture());

        Specialty specialtyCaptorValue = specialtyCaptor.getValue();
        assertNotNull(specialtyCaptorValue);
        assertEquals(specialtyCaptorValue.getId(), specialtyId);
        assertTrue(specialtyCaptorValue.getArchived());
    }
}
