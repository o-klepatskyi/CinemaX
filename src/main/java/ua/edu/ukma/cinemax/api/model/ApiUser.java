package ua.edu.ukma.cinemax.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.ukma.cinemax.persistance.model.User;

@Data
@NoArgsConstructor
public class ApiUser {
    private Long id;
    private String email;
    private String password;

    public ApiUser(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public User toModel() {
        User model = new User();
        model.setId(id);
        model.setEmail(email);
        model.setPassword(password);
        return model;
    }
}
