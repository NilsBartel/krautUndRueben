package org.example.gymbrobox.database;

import org.example.gymbrobox.database.rowMapper.ZutatNameRowMapper;
import org.example.gymbrobox.model.Zutat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ZutatRepo {
    private final JdbcTemplate jdbcTemplate;

    public ZutatRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Zutat> getAll() {
        String sql = "select z.BEZEICHNUNG, z.GEWICHT, z.EINHEIT from ZUTAT z;";

        return jdbcTemplate.query(sql, new ZutatNameRowMapper());
    }


}
