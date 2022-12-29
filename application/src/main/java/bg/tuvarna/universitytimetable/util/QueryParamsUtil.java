package bg.tuvarna.universitytimetable.util;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Field;
import java.util.Arrays;

@Component
public class QueryParamsUtil {

    public void attachQueryParams(RedirectAttributes attributes, Object object) {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        Arrays.stream(declaredFields).forEach(f -> {
            try {
                f.setAccessible(true);
                Object value = f.get(object);
                attributes.addAttribute(f.getName(), value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
