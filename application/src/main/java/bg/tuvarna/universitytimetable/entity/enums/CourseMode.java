package bg.tuvarna.universitytimetable.entity.enums;

public enum CourseMode {

    FULL_TIME("courseMode.fullTime"),
    PART_TIME("courseMode.partTime");

    private final String key;

    CourseMode(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
