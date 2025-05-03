package org.example.gymbrobox.database.rowMapper;

import org.example.gymbrobox.model.Zutat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ZutatRowMapper implements RowMapper<Zutat> {

    @Override
    public Zutat mapRow(ResultSet rs, int rowNum) throws SQLException {
        Zutat zutat = new Zutat();

        zutat.setName(rs.getString("BEZEICHNUNG"));
        zutat.setMenge(rs.getDouble("MENGE"));
        zutat.setEinheit(rs.getString("EINHEIT"));

        return zutat;
    }
}
