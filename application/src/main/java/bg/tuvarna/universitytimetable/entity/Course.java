package bg.tuvarna.universitytimetable.entity;

import bg.tuvarna.universitytimetable.entity.enums.CourseMode;
import bg.tuvarna.universitytimetable.entity.enums.CourseYear;
import bg.tuvarna.universitytimetable.entity.enums.Degree;
import bg.tuvarna.universitytimetable.entity.enums.CourseWeek;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "degree", nullable = false)
    private Degree degree;

    @Enumerated(EnumType.STRING)
    @Column(name = "class_year", nullable = false)
    private CourseYear year;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode", nullable = false)
    private CourseMode mode;

    @Enumerated(EnumType.STRING)
    @Column(name = "week", nullable = false)
    private CourseWeek week;

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

    @ManyToOne
    @JoinColumn(name = "specialty_id", referencedColumnName = "id", nullable = false)
    private Specialty specialty;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    private Subject subject;

    @ManyToMany
    @JoinTable(name = "course_group",
        joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    private List<Group> groups;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseTime> courseTimes;
}
