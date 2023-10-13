package monitoring_ozone.service;

import monitoring_ozone.constants.MailConstants;
import monitoring_ozone.model.Role;
import monitoring_ozone.model.User;
import monitoring_ozone.repository.UserRepository;
import monitoring_ozone.util.mailutil.MailUtil;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;

    public UserService(UserRepository repository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       JavaMailSender javaMailSender) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;
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

    public User getByLogin(String login) {
        return repository.findUserByLogin(login);
    }

    public User getByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    public void sendChangePasswordEmail(User user) {
        UUID uuid = UUID.randomUUID();
        user.setChangePasswordToken(uuid.toString());
        update(user);
        SimpleMailMessage mailMessage = MailUtil.createMailMessage(
                user.getEmail(),
                MailConstants.MAIL_SUBJECT_FOR_REMEMBER_PASSWORD,
                MailConstants.MAIL_MESSAGE_FOR_REMEMBER_PASSWORD + uuid
        );
        javaMailSender.send(mailMessage);
    }

    public User getUserByChangePasswordToken(String uuid) {
        return repository.findUserByChangePasswordToken(uuid);
    }

    public void changePassword(final String uuid, final String password) {
        User user = repository.findUserByChangePasswordToken(uuid);
        user.setChangePasswordToken(null);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        update(user);
    }
}
