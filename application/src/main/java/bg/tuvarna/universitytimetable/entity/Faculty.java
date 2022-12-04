package bg.tuvarna.universitytimetable.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "faculty")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_bg", nullable = false, unique = true)
    private String nameBg;

    @Column(name = "name_en", nullable = false, unique = true)
    private String nameEn;
}
