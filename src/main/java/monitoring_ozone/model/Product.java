package monitoring_ozone.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "products", schema = "ozone")
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(nullable = false, length = 1024)
    private String url;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "min_price")
    private Integer minPrice;

    @Column(name = "expected")
    private Integer expectedPrice;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Story> stories;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_PRODUCT_USER"))
    private User user;

    // сделать nullable = false;
    @ManyToOne
    @JoinColumn(name = "market_id", foreignKey = @ForeignKey(name = "FK_PRODUCT_MARKET"))
    private Market market;
}
