package org.example.gymbrobox.database;

import org.example.gymbrobox.model.UserAccount;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepo {
    private final NamedParameterJdbcTemplate template;

    public LoginRepo (NamedParameterJdbcTemplate template) {
        this.template = template;
    }


    public String getPasswordByUsername (UserAccount userAccount) {

        String sql = "SELECT PASSHASH FROM LOGIN WHERE USERNAME = 'wellensteyn';";

        MapSqlParameterSource params = new MapSqlParameterSource("username", userAccount.getPassword());

        return template.queryForObject(sql, params, String.class);
    }



}
