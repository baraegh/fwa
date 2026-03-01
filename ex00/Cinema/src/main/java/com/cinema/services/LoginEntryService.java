package com.cinema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cinema.models.LoginEntry;
import com.cinema.repositories.LoginEntryRepository;

@Component
public class LoginEntryService {
    @Autowired
    private LoginEntryRepository loginEntryRepository;

    public Optional<LoginEntry> getById(Long id) {
        return loginEntryRepository.findById(id);
    }

    public List<LoginEntry> getByUserId(Long userId) {
        return loginEntryRepository.findByUserId(userId);
    }

    public void deleteById(Long id) {
        loginEntryRepository.delete(id);
    }

    public LoginEntry save(LoginEntry loginEntry) {
        return loginEntryRepository.save(loginEntry);
    }

    public LoginEntry update(LoginEntry loginEntry) {
        return loginEntryRepository.update(loginEntry);
    }
}
