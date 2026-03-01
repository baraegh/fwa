package com.cinema.repositories;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.cinema.models.LoginEntry;

@Component
public class LoginEntryRepoImp implements LoginEntryRepository {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public LoginEntryRepoImp(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final RowMapper<LoginEntry> loginEntryRowmapper = (rs, rowNum) -> {
        LoginEntry loginEntry = new LoginEntry();
        loginEntry.setId(rs.getLong("id")); 
        loginEntry.setUserId(rs.getLong("user_id"));
        loginEntry.setIpAddress(rs.getString("ip_address"));
        loginEntry.setDate(rs.getDate("date").toLocalDate());
        loginEntry.setTime(rs.getTime("time").toLocalTime());
        return loginEntry;
    };

    @Override
    public LoginEntry save(LoginEntry entity) {
        String sql = "INSERT INTO login_history (user_id, ip_address, date, time) VALUES (:user_id, :ip_address, :date, :time)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", entity.getUserId());
        params.addValue("ip_address", entity.getIpAddress());
        params.addValue("date", entity.getDate());
        params.addValue("time", entity.getTime());
        namedJdbcTemplate.update(sql, params);

        return entity;
    }

    @Override
    public Optional<LoginEntry> findById(Long id) {
        String sql = "SELECT * FROM login_history WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        List<LoginEntry> loginEntries = namedJdbcTemplate.query(sql, params, loginEntryRowmapper);

        if (loginEntries.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(loginEntries.get(0));
    }

    @Override
    public LoginEntry update(LoginEntry entity) {
        String sql = "UPDATE login_history SET ip_address = :ip_address, date = :date, time = :time WHERE user_id = :user_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", entity.getUserId());
        params.addValue("ip_address", entity.getIpAddress());
        params.addValue("date", entity.getDate());
        params.addValue("time", entity.getTime());
        namedJdbcTemplate.update(sql, params);

        return entity;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM login_history WHERE user_id = :user_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", id);
        namedJdbcTemplate.update(sql, params);
    }

    @Override
    public List<LoginEntry> findAll() {
        String sql = "SELECT * FROM login_history";
        return namedJdbcTemplate.query(sql, loginEntryRowmapper);
    }

    @Override
    public List<LoginEntry> findByUserId(Long userId) {
        String sql = "SELECT * FROM login_history WHERE user_id = :user_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        return namedJdbcTemplate.query(sql, params, loginEntryRowmapper);
    }
    
}
