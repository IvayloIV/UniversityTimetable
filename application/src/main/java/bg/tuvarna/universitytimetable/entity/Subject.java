package bg.tuvarna.universitytimetable.entity;

import bg.tuvarna.universitytimetable.entity.enums.SubjectType;
import bg.tuvarna.universitytimetable.entity.enums.SubjectWeek;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "abbreviation_bg", nullable = false, unique = true, length = 63)
    private String abbreviationBg;

    @Column(name = "abbreviation_en", nullable = false, unique = true, length = 63)
    private String abbreviationEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SubjectType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "week", nullable = false)
    private SubjectWeek week;

    @Column(name = "start_week")
    private Short startWeek;

    @Column(name = "end_week")
    private Short endWeek;

    @Column(name = "hours_per_week", nullable = false)
    private Short hoursPerWeek;

    @Column(name = "meetings_per_week", nullable = false)
    private Short meetingsPerWeek;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "archived", nullable = false)
    private Boolean archived;
}
