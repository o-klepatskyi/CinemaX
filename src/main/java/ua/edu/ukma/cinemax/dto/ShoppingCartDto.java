package ua.edu.ukma.cinemax.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShoppingCartDto extends AbstractDto {
    private Long id;
    private List<TicketDto> tickets;
    private UserDto user;
}
