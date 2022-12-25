package bg.tuvarna.universitytimetable.entity.enums;

public enum SubjectType {

    LECTURE("subjectType.lecture", "subjectType.lectureAbbreviation"),
    EXERCISE("subjectType.exercise", "subjectType.exerciseAbbreviation"),
    COURSEWORK("subjectType.coursework", "subjectType.courseworkAbbreviation");

    private final String key;
    private final String abbreviation;

    SubjectType(String key, String abbreviation) {
        this.key = key;
        this.abbreviation = abbreviation;
    }

    public String getKey() {
        return key;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
