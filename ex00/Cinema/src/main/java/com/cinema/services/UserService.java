package com.cinema.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.cinema.Exceptions.InvalidCredentialsException;
import com.cinema.Exceptions.UserNotFoundException;
import com.cinema.models.User;
import com.cinema.repositories.UserRepository;
import com.cinema.repositories.UserServiceRepo;

@Component
public class UserService implements UserServiceRepo {
    @Autowired
    private UserRepository  userRepository;
    @Autowired
    BCryptPasswordEncoder   encoder;

    public User getById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        return userRepository.update(user);
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    public User signUp(User user) {
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User signIn(String email, String password) {
        User user =  userRepository.findUserByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }

        return user;
    }
}
