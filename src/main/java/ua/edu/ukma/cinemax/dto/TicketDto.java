package ua.edu.ukma.cinemax.dto;

import lombok.*;
import ua.edu.ukma.cinemax.persistance.entity.Session;
import ua.edu.ukma.cinemax.persistance.entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TicketDto extends AbstractDto {
    private Long id;
    private User user;
    private Session filmSession;
    private Integer aisle;
    private Integer seat;
    private Boolean isBought;
}
