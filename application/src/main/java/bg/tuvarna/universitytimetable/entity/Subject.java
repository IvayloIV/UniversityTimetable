package bg.tuvarna.universitytimetable.entity;

import bg.tuvarna.universitytimetable.entity.enums.SubjectType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_bg", nullable = false, length = 63)
    private String nameBg;

    @Column(name = "name_en", nullable = false, length = 63)
    private String nameEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SubjectType type;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "archived", nullable = false)
    private Boolean archived;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Course> courses;
}
