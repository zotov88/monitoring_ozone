package monitoring_ozone.repository;

import monitoring_ozone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByLogin(String login);

    @Query(nativeQuery = true,
    value = """
            select *
            from ozone.users
            where id = :id
            """)
    User findUserById(Long id);
}
