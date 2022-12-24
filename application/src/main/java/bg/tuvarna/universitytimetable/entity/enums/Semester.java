package bg.tuvarna.universitytimetable.entity.enums;

public enum Semester {

    WINTER("semester.winter"),
    SUMMER("semester.summer");

    private final String key;

    Semester(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
