package com.cinema.repositories;

import java.util.List;
import java.util.Optional;

import com.cinema.models.Image;

public interface ImageRepository extends CrudRepository<Image> {
    List<Image>         findByUserId(Long userId);
    Optional<Image>     findByFileName(String fileName);
}
