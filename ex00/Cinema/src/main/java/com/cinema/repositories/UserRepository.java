package com.cinema.repositories;

import java.util.Optional;

import com.cinema.models.User;

public interface UserRepository extends CrudRepository<User> {
    Optional<User> findUserByEmail(String email);
}
