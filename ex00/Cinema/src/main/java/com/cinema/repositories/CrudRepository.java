package com.cinema.repositories;

import java.util.List;

public interface CrudRepository<T> {
    T save(T entity);
    T findById(Long id);
    T update(T entity);
    void delete(Long id);
    List<T> findAll();
}
