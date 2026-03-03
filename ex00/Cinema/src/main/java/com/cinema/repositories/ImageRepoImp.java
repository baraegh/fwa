package com.cinema.repositories;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.cinema.models.Image;

@Component
public class ImageRepoImp implements ImageRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ImageRepoImp(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final RowMapper<Image> imageRowMapper = (rs, rowNum) -> {
        Image image = new Image();
        image.setId(rs.getLong("id"));
        image.setUserId(rs.getLong("user_id"));
        image.setFileName(rs.getString("file_name"));
        image.setMimeType(rs.getString("mime_type"));
        image.setSize(rs.getLong("size"));
        image.setFilePath(rs.getString("file_path"));
        return image;
    };

    @Override
    public Image save(Image entity) {
        String sql = "INSERT INTO images (user_id, file_name, mime_type, size, file_path) VALUES (:user_id, :file_name, :mime_type, :size, :file_path)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", entity.getUserId());
        params.addValue("file_name", entity.getFileName());
        params.addValue("mime_type", entity.getMimeType());
        params.addValue("size", entity.getSize());
        params.addValue("file_path", entity.getFilePath());
        jdbcTemplate.update(sql, params);
        return entity;
    }

    @Override
    public Optional<Image> findById(Long id) {
        String sql = "SELECT * FROM images WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<Image> images = jdbcTemplate.query(sql, params, imageRowMapper);
        if (images.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(images.get(0));
    }

    @Override
    public Image update(Image entity) {
        String sql = "UPDATE images SET user_id = :user_id, file_name = :file_name, mime_type = :mime_type, size = :size, file_path = :file_path WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", entity.getUserId());
        params.addValue("file_name", entity.getFileName());
        params.addValue("mime_type", entity.getMimeType());
        params.addValue("size", entity.getSize());
        params.addValue("file_path", entity.getFilePath());
        params.addValue("id", entity.getId());
        jdbcTemplate.update(sql, params);
        return entity;  
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM images WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public List<Image> findAll() {
        String sql = "SELECT * FROM images";
        return jdbcTemplate.query(sql, imageRowMapper);
    }

    @Override
    public List<Image> findByUserId(Long userId) {
        String sql = "SELECT * FROM images WHERE user_id = :user_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        return jdbcTemplate.query(sql, params, imageRowMapper);
    }

    @Override
    public Optional<Image> findByFileName(String fileName) {
        String sql = "SELECT * FROM images WHERE file_name = :file_name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("file_name", fileName);
        
        List<Image> images = jdbcTemplate.query(sql, params, imageRowMapper);
        if (images.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(images.get(0));
    }
    
}
