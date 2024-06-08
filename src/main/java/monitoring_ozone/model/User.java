package monitoring_ozone.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users", schema = "ozone")
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    private String changePasswordToken;

    private String email;

    @Column(name = "tg_token", nullable = false)
    private String tgBotToken;

    @Column(name = "tg_chat_id", nullable = false)
    private Long tgChatId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_ROLE"))
    private Role role;
}
