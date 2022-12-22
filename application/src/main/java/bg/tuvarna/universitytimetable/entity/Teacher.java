package bg.tuvarna.universitytimetable.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "teacher")
@ToString(exclude={"user", "teacherFreeTime"})
@EqualsAndHashCode(exclude={"user", "teacherFreeTime"})
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "academic_rank_bg", nullable = false, length = 63)
    private String academicRankBg;

    @Column(name = "academic_rank_en", nullable = false, length = 63)
    private String academicRankEn;

    @Column(name = "first_name_bg", nullable = false, length = 63)
    private String firstNameBg;

    @Column(name = "first_name_en", nullable = false, length = 63)
    private String firstNameEn;

    @Column(name = "last_name_bg", nullable = false, length = 63)
    private String lastNameBg;

    @Column(name = "last_name_en", nullable = false, length = 63)
    private String lastNameEn;

    @Column(name = "ucn", nullable = false, length = 10)
    private String ucn;

    @Column(name = "archived", nullable = false)
    private Boolean archived;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<TeacherFreeTime> teacherFreeTime;
}
