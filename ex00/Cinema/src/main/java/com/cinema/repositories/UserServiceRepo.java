package com.cinema.repositories;


import java.util.Optional;

import com.cinema.models.User;

public interface UserServiceRepo {
    Optional<User> signUp(User user);
    Optional<User> signIn(String email, String password);
}
