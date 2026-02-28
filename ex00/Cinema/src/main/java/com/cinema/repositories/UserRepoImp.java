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
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setEmail(rs.getString("email"));
            user.setPhoneNumber(rs.getString("phoneNumber"));
            user.setPassword(rs.getString("password"));
            return user;
        };


    @Override
    public User save(User entity) {
        String sql = "INSERT INTO users (first_name, last_name, email, phone_number, password) VALUES (:firstName, :lastName, :email , :phoneNumber, :password)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("firstName", entity.getFirstName());
        params.addValue("lastName", entity.getLastName());
        params.addValue("email", entity.getEmail());
        params.addValue("phoneNumber", entity.getPhoneNumber());
        params.addValue("password", entity.getPassword());
        namedJdbcTemplate.update(sql, params);
        return entity;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        List<User> users = namedJdbcTemplate.query(sql, params, usersRowmapper);

        if (users.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(users.get(0));
    }

    @Override
    public User update(User entity) {
        String sql = "UPDATE users SET first_name = :firstName, last_name = :lastName, email = :email, phone_number = :phoneNumber, password = :password WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", entity.getId());
        params.addValue("firstName", entity.getFirstName());
        params.addValue("lastName", entity.getLastName());
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
