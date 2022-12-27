package bg.tuvarna.universitytimetable.config;

import bg.tuvarna.universitytimetable.interceptor.PasswordUpdateInterceptor;
import bg.tuvarna.universitytimetable.interceptor.ScheduleLanguageInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final PasswordUpdateInterceptor passwordUpdateInterceptor;
    private final ScheduleLanguageInterceptor scheduleLanguageInterceptor;

    public WebConfig(PasswordUpdateInterceptor passwordUpdateInterceptor,
                     ScheduleLanguageInterceptor scheduleLanguageInterceptor) {
        this.passwordUpdateInterceptor = passwordUpdateInterceptor;
        this.scheduleLanguageInterceptor = scheduleLanguageInterceptor;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("locale/global");
        source.setDefaultEncoding("Windows-1251");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(new Locale("bg"));
        return sessionLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(passwordUpdateInterceptor)
                .excludePathPatterns("/", "/user/**", "/webjars/**", "/js/**", "/images/**", "/department/faculty/{id}",
                        "/specialty/department/{departmentId}", "/schedule/list/students");
        registry.addInterceptor(scheduleLanguageInterceptor)
                .excludePathPatterns("/", "/webjars/**", "/js/**", "/images/**","/department/faculty/{id}",
                        "/specialty/department/{departmentId}", "/schedule/list/students");
    }
}
