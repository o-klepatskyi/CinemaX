package ua.edu.ukma.cinemax.dto;

import lombok.*;
import ua.edu.ukma.cinemax.commons.AbstractDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto extends AbstractDto {
    private Long id;
    @NotEmpty(message = "Username should not be empty")
    private String username;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    private List<String> roles;
}
