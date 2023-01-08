
package bg.tuvarna.universitytimetable.web.controller.rest;

import bg.tuvarna.universitytimetable.entity.Subject;
import bg.tuvarna.universitytimetable.entity.User;
import bg.tuvarna.universitytimetable.entity.enums.Role;
import bg.tuvarna.universitytimetable.entity.enums.SubjectType;
import bg.tuvarna.universitytimetable.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SubjectRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        this.subjectRepository.deleteAll();
    }

    @Test
    public void deactivateSubject_withValidId_expectToExecuteSuccessful() throws Exception {
        String userPassword = passwordEncoder.encode("1234");
        User user = new User();
        user.setEmail("ivanov@gmail.com");
        user.setPassword(userPassword);
        user.setPasswordUpdatedDate(LocalDateTime.now());
        user.setRole(Role.ADMIN);
        Authentication auth = new TestingAuthenticationToken(user, userPassword);
        SecurityContextHolder.getContext().setAuthentication(auth);

        Subject subject = new Subject();
        subject.setNameBg("ИМУ");
        subject.setNameEn("NMD");
        subject.setType(SubjectType.LECTURE);
        subject.setCreatedDate(LocalDateTime.now());
        subject.setActive(true);
        subject.setArchived(false);
        subjectRepository.saveAndFlush(subject);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/subject/update/status/{id}", subject.getId())
            )
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string("Промяната е направена успешно."));

        Subject actual = subjectRepository.findById(subject.getId()).orElse(null);
        assertNotNull(actual);
        assertFalse(actual.getActive());
    }
}
