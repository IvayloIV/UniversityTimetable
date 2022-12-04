package bg.tuvarna.universitytimetable.entity;

import bg.tuvarna.universitytimetable.entity.enums.CourseMode;
import bg.tuvarna.universitytimetable.entity.enums.CourseYear;
import bg.tuvarna.universitytimetable.entity.enums.Degree;
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

    @Column(name = "degree", nullable = false)
    private Degree degree;

    @Column(name = "year", nullable = false)
    private CourseYear year;

    @Column(name = "mode", nullable = false)
    private CourseMode mode;

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
}
