package org.example.gymbrobox.database.rowMapper;

import org.example.gymbrobox.model.Recipe;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipieRowMapper implements RowMapper<Recipe> {


    @Override
    public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
        Recipe recipe = new Recipe();

        recipe.setName(rs.getString("NAME"));
        //recipe.setDescription(rs.getString("BESCHREIBUNG"));
        recipe.setPreparation(rs.getString("VORGEHEN"));
        //recipe.setCo2(rs.getDouble("CO2"));
        //recipe.setPrice(rs.getDouble("PRICE"));

        return recipe;
    }
}
