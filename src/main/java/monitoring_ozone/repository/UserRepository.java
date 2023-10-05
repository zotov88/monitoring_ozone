package monitoring_ozone.repository;

import monitoring_ozone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByLogin(String login);

    User findUserByEmail(String email);

    User findUserByChangePasswordToken(String uuid);
}
