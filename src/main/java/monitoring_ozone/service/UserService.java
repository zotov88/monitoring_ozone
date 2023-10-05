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

    public User getById(final Long id) {
        return repository.getReferenceById(id);
    }

    public void create(final User user) {
        Role role = new Role();
        role.setId(1L);
        user.setRole(role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    public void update(final User user) {
        repository.save(user);
    }

    public User findByLogin(String login) {
        return repository.findUserByLogin(login);
    }

    public User getUser(Long id) {
        return repository.findUserById(id);
    }
}
