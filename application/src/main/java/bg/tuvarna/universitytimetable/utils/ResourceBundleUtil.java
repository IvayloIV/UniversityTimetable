package bg.tuvarna.universitytimetable.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ResourceBundleUtil {

    private final MessageSource messageSource;

    @Autowired
    public ResourceBundleUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return this.messageSource.getMessage(key, args, locale);
    }
}
