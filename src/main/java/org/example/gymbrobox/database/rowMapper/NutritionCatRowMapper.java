package org.example.gymbrobox.database.rowMapper;

import org.example.gymbrobox.model.NutritionCat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NutritionCatRowMapper implements RowMapper<NutritionCat> {

    @Override
    public NutritionCat mapRow(ResultSet rs, int rowNum) throws SQLException {
        NutritionCat nutritionCat = new NutritionCat();

        nutritionCat.setName(rs.getString("BEZEICHNUNG"));
        nutritionCat.setDescription(rs.getString("BESCHREIBUNG"));

        return nutritionCat;
    }

}
