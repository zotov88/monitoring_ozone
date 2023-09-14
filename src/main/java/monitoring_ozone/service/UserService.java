package monitoring_ozone.service;

import monitoring_ozone.model.Role;
import monitoring_ozone.model.User;
import monitoring_ozone.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository repository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void create(User user) {
        Role role = new Role();
        role.setId(1L);
        user.setRole(role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    public User findByLogin(String login) {
        return repository.findUserByLogin(login);
    }
}
