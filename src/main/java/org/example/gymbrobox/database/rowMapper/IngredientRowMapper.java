package org.example.gymbrobox.database.rowMapper;

import org.example.gymbrobox.model.Ingredients;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientRowMapper implements RowMapper<Ingredients> {

    @Override
    public Ingredients mapRow(ResultSet rs, int rowNum) throws SQLException {
        Ingredients ingredient = new Ingredients();

        ingredient.setName(rs.getString("BEZEICHNUNG"));
        ingredient.setPrice(rs.getDouble("NETTOPREIS"));
        //ingredient.setQuantity(rs.getInt("MENGE"));
        ingredient.setWeight(rs.getDouble("GEWICHT"));
        ingredient.setUnit(rs.getString("EINHEIT"));
        return ingredient;
    }




}

