package org.example.gymbrobox.database.rowMapper;

import org.example.gymbrobox.model.Recipie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipieRowMapper implements RowMapper<Recipie> {


    @Override
    public Recipie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Recipie recipie = new Recipie();

        recipie.setName(rs.getString("BEZEICHNUNG"));
        recipie.setDescription(rs.getString("BESCHREIBUNG"));
        recipie.setPreparation(rs.getString("PREPARATION"));
        recipie.setCo2(rs.getDouble("CO2"));
        recipie.setPrice(rs.getDouble("PRICE"));


        return recipie;
    }
}
