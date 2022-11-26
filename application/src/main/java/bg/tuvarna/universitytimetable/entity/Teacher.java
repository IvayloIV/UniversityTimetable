package bg.tuvarna.universitytimetable.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "ucn", nullable = false, unique = true, length = 10)
    private String ucn;

    @Column(name = "archived", nullable = false)
    private Boolean archived;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}
