package ua.edu.ukma.cinemax.persistance.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shopping_carts")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class ShoppingCart extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    @ToString.Exclude
    private List<Ticket> tickets;
    @OneToOne
    @MapsId
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ShoppingCart that = (ShoppingCart) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
