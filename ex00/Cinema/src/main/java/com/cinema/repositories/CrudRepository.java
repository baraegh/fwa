package com.cinema.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> save(T entity);
    Optional<T>findById(Long id);
    Optional<T> update(T entity);
    void delete(Long id);
    Optional<List<T>> findAll();
}
