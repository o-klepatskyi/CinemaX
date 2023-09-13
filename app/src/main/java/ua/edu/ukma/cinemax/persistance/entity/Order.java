package ua.edu.ukma.cinemax.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ua.edu.ukma.cinemax.commons.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Order extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @OneToMany
    private List<Ticket> tickets;
    private LocalDateTime orderTime;
}
