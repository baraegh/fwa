package com.cinema.repositories;


import com.cinema.models.User;

public interface UserServiceRepo {
    User signUp(User user);
    User signIn(String email, String password);
}
