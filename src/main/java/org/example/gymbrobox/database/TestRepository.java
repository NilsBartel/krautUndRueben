package org.example.gymbrobox.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository {
    private JdbcOperations jdbcTemplate;





    public int getKundeCount() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM KUNDE",
                Integer.class);
    }

    public int getLieferantCount() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM LIEFERANT",
                Integer.class);
    }











    public JdbcOperations getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}


