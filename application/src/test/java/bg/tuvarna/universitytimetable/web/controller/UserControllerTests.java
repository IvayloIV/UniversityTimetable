package bg.tuvarna.universitytimetable.web.controller;

import bg.tuvarna.universitytimetable.entity.User;
import bg.tuvarna.universitytimetable.entity.enums.Role;
import bg.tuvarna.universitytimetable.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    public void init() {
        this.userRepository.deleteAll();
    }

    @Test
    public void updateUserPassword_withValidArgs_expectToExecuteSuccessful() throws Exception {
        String email = "ivanov@gmail.com";
        String oldPasswordPlain = "1234";
        String oldPasswordEncoded = encoder.encode(oldPasswordPlain);
        String newPasswordPlain = "12345";

        User user = new User();
        user.setEmail(email);
        user.setPassword(oldPasswordEncoded);
        user.setRole(Role.ADMIN);
        userRepository.saveAndFlush(user);

        Authentication auth = new TestingAuthenticationToken(user, oldPasswordEncoded);
        SecurityContextHolder.getContext().setAuthentication(auth);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/password/update")
                    .param("oldPassword", oldPasswordPlain)
                    .param("newPassword", newPasswordPlain)
                    .param("confirmPassword", newPasswordPlain)
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));

        User actual = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(actual);

        boolean matches = encoder.matches(newPasswordPlain, actual.getPassword());
        assertTrue(matches);
        assertNotNull(actual.getPasswordUpdatedDate());
    }
}
