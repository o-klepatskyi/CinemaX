package com.example.cinemax.repository.impl;

import lombok.*;

@AllArgsConstructor
public class UserCredentials {
    @NonNull
    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    @NonNull
    private String password;
}
