package org.example.gymbrobox.database.rowMapper;

import org.example.gymbrobox.model.Allergy;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AllergyRowMapper implements RowMapper<Allergy> {

    @Override
    public Allergy mapRow(ResultSet rs, int rowNum) throws SQLException {
        Allergy allergy = new Allergy();

        allergy.setName(rs.getString("BEZEICHNUNG"));
        allergy.setDescription(rs.getString("BESCHREIBUNG"));

        return allergy;
    }
}
