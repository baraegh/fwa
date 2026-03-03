package com.cinema.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cinema.models.Image;
import com.cinema.repositories.ImageRepository;

@Component
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Value("${storage.path}")
    private String  storagePath;


    public Image save(Image image, InputStream fileData) throws IOException {
        String extension = image.getFileName().substring(image.getFileName().lastIndexOf("."));
        String name = image.getFileName().substring(0, image.getFileName().lastIndexOf("."));

        String imgId =  name + UUID.randomUUID().toString() + extension;
        Path path = Paths.get(storagePath, imgId);        
        Files.createDirectories(path.getParent());
        Files.copy(fileData, path, StandardCopyOption.REPLACE_EXISTING);

        image.setFileName(imgId);
        image.setFilePath(path.toString());
        return imageRepository.save(image);
    }

    public Image getById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    public Image update(Image image) {
        return imageRepository.update(image);
    }

    public void deleteById(Long id) {
        imageRepository.delete(id);
    }

    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    public List<Image> getByUserId(Long userId) {
        return imageRepository.findByUserId(userId);
    }

    public Optional<Image> getByFileName(String fileName) {
        return imageRepository.findByFileName(fileName);
    }
}
