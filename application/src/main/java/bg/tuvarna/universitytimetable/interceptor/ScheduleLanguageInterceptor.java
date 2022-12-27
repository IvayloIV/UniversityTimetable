package bg.tuvarna.universitytimetable.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;

@Component
public class ScheduleLanguageInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session != null && Locale.ENGLISH.equals(session.getAttribute(LOCALE_SESSION_ATTRIBUTE_NAME))) {
            session.setAttribute(LOCALE_SESSION_ATTRIBUTE_NAME, new Locale("bg"));
        }
        return true;
    }
}
