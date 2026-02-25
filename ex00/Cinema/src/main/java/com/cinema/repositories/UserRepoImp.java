package com.cinema.repositories;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.cinema.models.User;

@Component
public class UserRepoImp implements UserRepository {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public UserRepoImp(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final RowMapper<User> usersRowmapper = (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPhoneNumber(rs.getString("phoneNumber"));
            user.setPassword(rs.getString("password"));
            return user;
        };


    @Override
    public User save(User entity) {
        String sql = "INSERT INTO users (name, email, phoneNumber, password) VALUES (:name, :email: , :phoneNumber, :password)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", entity.getName());
        params.addValue("email", entity.getEmail());
        params.addValue("phoneNumber", entity.getPhoneNumber());
        params.addValue("password", entity.getPassword());
        namedJdbcTemplate.update(sql, params);
        return entity;
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return namedJdbcTemplate.queryForObject(sql, params, usersRowmapper);
    }

    @Override
    public User update(User entity) {
        String sql = "UPDATE users SET name = :name, email = :email, phoneNumber = :phoneNumber, password = :password WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", entity.getId());
        params.addValue("name", entity.getName());
        params.addValue("email", entity.getEmail());
        params.addValue("phoneNumber", entity.getPhoneNumber());
        params.addValue("password", entity.getPassword());
        namedJdbcTemplate.update(sql, params);
        return entity;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        namedJdbcTemplate.update(sql, params);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return namedJdbcTemplate.query(sql, usersRowmapper);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        String sql = "Select * from users where email = :email";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);

        List<User> users =  namedJdbcTemplate.query(sql, params, usersRowmapper);

        return users.stream().findFirst();
    }
    
}
