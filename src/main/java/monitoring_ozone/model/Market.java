package monitoring_ozone.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "markets", schema = "ozone")
@NoArgsConstructor
@Getter
@Setter
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "market", cascade = CascadeType.REMOVE)
    private List<Product> products;

}
