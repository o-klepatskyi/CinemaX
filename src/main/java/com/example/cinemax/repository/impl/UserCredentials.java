package com.example.cinemax.repository.impl;

import lombok.*;

@Data
@AllArgsConstructor
public class UserCredentials {
    @NonNull
    private String login;

    @Getter
    private String password;
}
