package bg.tuvarna.universitytimetable.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name_bg", nullable = false, unique = true)
    private String nameBg;

    @Column(name = "name_en", nullable = false, unique = true)
    private String nameEn;

    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id", nullable = false)
    private Faculty faculty;
}
