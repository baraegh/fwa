package com.cinema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.cinema.models.User;
import com.cinema.repositories.UserRepository;
import com.cinema.repositories.UserServiceRepo;

@Component
public class UserService implements UserServiceRepo {
    @Autowired
    private UserRepository      userRepository;
    @Autowired
    BCryptPasswordEncoder       encoder;

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getByEmail(String email) {
        return userRepository.findUserByEmail(email);
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
    public Optional<User> signUp(User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            return Optional.empty();
        }

        user.setPassword(encoder.encode(user.getPassword()));
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> signIn(String email, String password) {
        return userRepository.findUserByEmail(email)
                .filter(user -> encoder.matches(password, user.getPassword()));
    }
}
