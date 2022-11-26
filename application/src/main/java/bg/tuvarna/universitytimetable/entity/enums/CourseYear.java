package bg.tuvarna.universitytimetable.entity.enums;

public enum CourseYear {
    FRESHMAN("I"),
    SOPHOMORE("II"),
    JUNIOR("III"),
    SENIOR("IV");

    private String value;

    CourseYear(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
