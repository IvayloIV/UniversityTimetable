package bg.tuvarna.universitytimetable.repository;

import bg.tuvarna.universitytimetable.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findAllByNameIgnoreCase(String name);
}
