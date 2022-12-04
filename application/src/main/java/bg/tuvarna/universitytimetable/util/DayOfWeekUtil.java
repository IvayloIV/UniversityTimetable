package bg.tuvarna.universitytimetable.util;

import org.springframework.context.i18n.LocaleContextHolder;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public class DayOfWeekUtil {

    public static String[] getLocaleDays() {
        Locale locale = LocaleContextHolder.getLocale();
        return Arrays.stream(DayOfWeek.values())
            .map(d -> {
                String displayName = d.getDisplayName(TextStyle.FULL, locale);
                return displayName.substring(0, 1).toUpperCase() + displayName.substring(1).toLowerCase();
            })
            .toArray(String[]::new);
    }
}
