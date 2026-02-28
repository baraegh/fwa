package com.cinema.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    T save(T entity);
    Optional<T>findById(Long id);
    T update(T entity);
    void delete(Long id);
    List<T> findAll();
}
