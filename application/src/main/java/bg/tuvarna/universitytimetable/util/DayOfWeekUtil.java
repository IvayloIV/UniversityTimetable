package bg.tuvarna.universitytimetable.util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Component
public class DayOfWeekUtil {

    public static String[] getLocaleDays() {
        Locale locale = LocaleContextHolder.getLocale();
        return getWorkDays().stream()
            .map(d -> {
                String displayName = d.getDisplayName(TextStyle.FULL, locale);
                return displayName.substring(0, 1).toUpperCase() + displayName.substring(1).toLowerCase();
            })
            .toArray(String[]::new);
    }

    public static String getLocaleDay(DayOfWeek day) {
        Locale locale = LocaleContextHolder.getLocale();
        String displayName = day.getDisplayName(TextStyle.FULL, locale);
        return displayName.substring(0, 1).toUpperCase() + displayName.substring(1).toLowerCase();
    }

    public static String getDay(DayOfWeek day, String language) {
        String displayName = day.getDisplayName(TextStyle.FULL, new Locale(language));
        return displayName.substring(0, 1).toUpperCase() + displayName.substring(1).toLowerCase();
    }

    public static List<DayOfWeek> getWorkDays() {
        return List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
    }
}
