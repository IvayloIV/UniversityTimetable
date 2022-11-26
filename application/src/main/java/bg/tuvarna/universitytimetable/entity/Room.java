package bg.tuvarna.universitytimetable.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "number_bg", nullable = false, unique = true, length = 15)
    private String numberBg;

    @Column(name = "number_en", nullable = false, unique = true, length = 15)
    private String numberEn;
}
