package com.cinema.repositories;

import java.util.List;

import com.cinema.models.LoginEntry;

public interface LoginEntryRepository extends CrudRepository<LoginEntry> {
    List<LoginEntry> findByUserId(Long userId);
}
