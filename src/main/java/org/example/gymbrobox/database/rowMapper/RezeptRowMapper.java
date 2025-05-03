package org.example.gymbrobox.database.rowMapper;

import org.example.gymbrobox.model.Rezept;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RezeptRowMapper implements RowMapper<Rezept> {

    @Override
    public Rezept mapRow(ResultSet rs, int rowNum) throws SQLException {
        Rezept rezept = new Rezept();

        rezept.setName(rs.getString("NAME"));
        rezept.setBeschreibung(rs.getString("BESCHREIBUNG"));
        rezept.setZubereitung(rs.getString("VORGEHEN"));

        return rezept;
    }
}
