package ua.edu.ukma.cinemax.dto;

import lombok.*;
import ua.edu.ukma.cinemax.commons.AbstractDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto extends AbstractDto {
    private Long id;
    private UserDto user;
    private List<TicketDto> tickets;
    private LocalDateTime orderTime;
}
