package bg.tuvarna.universitytimetable.entity;

import bg.tuvarna.universitytimetable.entity.enums.SubjectType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "subject")
@EqualsAndHashCode(exclude={"courses"})
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Course> courses;
}
