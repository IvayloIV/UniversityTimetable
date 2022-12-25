package bg.tuvarna.universitytimetable.entity.enums;

public enum Degree {

    BACHELOR_BG("degree.bachelorBg", "bg"),
    BACHELOR_EN("degree.bachelorEn", "en"),
    MASTER_BG_1_5("degree.masterBg1.5", "bg"),
    MASTER_EN_1_5("degree.masterEn1.5", "en"),
    MASTER_BG_2_5("degree.masterBg2.5", "bg"),
    MASTER_EN_2_5("degree.masterEn2.5", "en");

    private final String key;
    private final String language;

    Degree(String key, String language) {
        this.key = key;
        this.language = language;
    }

    public String getKey() {
        return key;
    }

    public String getLanguage() {
        return language;
    }
}
