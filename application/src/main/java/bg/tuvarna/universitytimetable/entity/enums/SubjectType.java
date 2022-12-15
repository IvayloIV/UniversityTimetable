package bg.tuvarna.universitytimetable.entity.enums;

public enum SubjectType {

    LECTURE("subjectType.lecture"),
    EXERCISE("subjectType.exercise"),
    COURSEWORK("subjectType.coursework");

    private final String key;

    SubjectType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
