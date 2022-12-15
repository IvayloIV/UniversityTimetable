package bg.tuvarna.universitytimetable.entity.enums;

public enum CourseWeek {

    ALL("courseWeek.all"),
    EVEN("courseWeek.even"),
    ODD("courseWeek.odd");

    private final String key;

    CourseWeek(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
