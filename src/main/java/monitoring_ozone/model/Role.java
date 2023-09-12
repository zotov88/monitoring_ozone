package monitoring_ozone.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "roles", schema = "ozone")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role", cascade = CascadeType.REMOVE)
    private List<User> users;
}
