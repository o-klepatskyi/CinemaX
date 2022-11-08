package ua.edu.ukma.cinemax.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.ukma.cinemax.model.CinemaHall;

@Data
@NoArgsConstructor
public class ApiCinemaHall {
    private Long id;
    private int capacity;
    private String description;

    public ApiCinemaHall(CinemaHall cinemaHall) {
        this.id = cinemaHall.getId();
        this.capacity = cinemaHall.getCapacity();
        this.description = cinemaHall.getDescription();
    }

    public CinemaHall toModel() {
        CinemaHall model = new CinemaHall();
        model.setId(id);
        model.setCapacity(capacity);
        model.setDescription(description);
        return model;
    }
}
