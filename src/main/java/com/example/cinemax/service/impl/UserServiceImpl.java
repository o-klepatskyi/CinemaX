package com.example.cinemax.service.impl;

import com.example.cinemax.repository.UserRepository;
import com.example.cinemax.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    // field inject
    @Autowired
    private UserRepository userRepository;
}
