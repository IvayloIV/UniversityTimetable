package bg.tuvarna.universitytimetable.entity.enums;

public enum Degree {

    BACHELOR_BG("degree.bachelorBg"),
    BACHELOR_EN("degree.bachelorEn"),
    MASTER_BG_1_5("degree.masterBg1.5"),
    MASTER_EN_1_5("degree.masterEn1.5"),
    MASTER_BG_2_5("degree.masterBg2.5"),
    MASTER_EN_2_5("degree.masterEn2.5");

    private final String key;

    Degree(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
