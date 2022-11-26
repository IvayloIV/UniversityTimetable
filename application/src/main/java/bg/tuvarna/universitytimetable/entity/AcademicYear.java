package bg.tuvarna.universitytimetable.entity;

import bg.tuvarna.universitytimetable.entity.enums.Semester;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "academic_year",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"year", "semester"})
        }
)
public class AcademicYear {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "year", nullable = false)
    private Short year;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester", nullable = false)
    private Semester semester;
}
