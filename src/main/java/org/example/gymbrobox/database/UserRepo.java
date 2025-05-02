package org.example.gymbrobox.database;

import org.example.gymbrobox.database.rowMapper.UserRowMapper;
import org.example.gymbrobox.model.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepo {

    private final NamedParameterJdbcTemplate template;

    public UserRepo(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public User findByUsername(String username) {
        String sql = "SELECT k.*, a.*, l.USERNAME " +
                            "FROM KUNDE k " +
                            "LEFT JOIN ADRESSE a ON k.ADRESSNR = a.ADRESSENR " +
                            "LEFT JOIN LOGIN l ON k.USERNAME = l.USERNAME " +
                            "WHERE k.USERNAME = :username;";


        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", username);

        User user = template.queryForObject(sql, params, new UserRowMapper());
        return user;
    }




}
