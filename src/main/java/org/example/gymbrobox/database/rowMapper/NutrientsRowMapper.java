package org.example.gymbrobox.database.rowMapper;

import org.example.gymbrobox.model.Nutrients;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;



public class NutrientsRowMapper implements RowMapper<Nutrients> {

    @Override
    public Nutrients mapRow(ResultSet rs, int rowNum) throws SQLException {
        Nutrients nutrients = new Nutrients();

        nutrients.setName(rs.getString("BEZEICHNUNG"));
        nutrients.setDescription(rs.getString("BESCHREIBUNG"));

        return nutrients;
    }
}
