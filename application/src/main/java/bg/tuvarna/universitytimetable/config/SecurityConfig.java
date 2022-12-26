package bg.tuvarna.universitytimetable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String LOGIN_PAGE = "/user/login";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf()
                .csrfTokenRepository(csrfTokenRepository())
            .and()
                .authorizeRequests()
                .antMatchers("/webjars/**", "/js/**", "/department/faculty/{id}", "/specialty/department/{departmentId}").permitAll()
                .antMatchers("/user/login").anonymous()
                .antMatchers("/teacher/create", "/teacher/delete/{id}", "/specialty/create", "/specialty/delete/{id}", "/subject/create",
                        "/department/{id}", "/specialty/{id}", "/subject/update/status/{id}", "/subject/delete/{id}", "/course/update/status/{id}",
                        "/course/delete/{id}", "/schedule/generate", "/schedule/save").hasAuthority("ADMIN")
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage(LOGIN_PAGE)
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/user/password/update")
                .failureHandler(this::onAuthenticationFailure)
            .and()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/user/login");

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository =
                new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }

    private void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.sendRedirect(LOGIN_PAGE + "?loginError=true");
    }
}