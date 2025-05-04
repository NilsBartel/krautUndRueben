package org.example.gymbrobox.database.rowMapper;

import org.example.gymbrobox.model.Rezept;
import org.example.gymbrobox.model.Zutat;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RezeptWithZutatenExtractor implements ResultSetExtractor<List<Rezept>> {
    @Override
    public List<Rezept> extractData(ResultSet rs) throws SQLException {
        Map<Integer, Rezept> rezeptMap = new HashMap<>();

        while (rs.next()) {
            int rezeptId = rs.getInt("REZEPTNR");

            Rezept rezept = rezeptMap.get(rezeptId);
            if (rezept == null) {
                rezept = new Rezept();
                rezept.setName(rs.getString("NAME"));
                rezept.setBeschreibung(rs.getString("BESCHREIBUNG"));
                rezept.setZubereitung(rs.getString("VORGEHEN"));
                rezept.setZutaten(new ArrayList<>());
                rezeptMap.put(rezeptId, rezept);
            }

            Zutat zutat = new Zutat();
            zutat.setName(rs.getString("BEZEICHNUNG"));
            zutat.setMenge(rs.getDouble("MENGE"));
            zutat.setEinheit(rs.getString("EINHEIT"));

            rezept.getZutaten().add(zutat);
        }

        return new ArrayList<>(rezeptMap.values());
    }
}
