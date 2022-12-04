package bg.tuvarna.universitytimetable.repository;

import bg.tuvarna.universitytimetable.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u " +
            "left join u.teacher t " +
            "where u.email = :email " +
            " and (u.role = 'ADMIN' or t.archived = false)")
    Optional<User> findByEmail(@Param("email") String email);
}
