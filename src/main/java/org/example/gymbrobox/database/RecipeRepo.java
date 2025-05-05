package org.example.gymbrobox.database;

import org.example.gymbrobox.database.rowMapper.RezeptWithZutatenExtractor;
import org.example.gymbrobox.model.CustomZutat;
import org.example.gymbrobox.model.Rezept;
import org.example.gymbrobox.model.Zutat;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RecipeRepo {
    private final NamedParameterJdbcTemplate template;

    public RecipeRepo (NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.template = namedParameterJdbcTemplate;
    }


    public List<Rezept> getFilteredRezeptList (String sql, MapSqlParameterSource params) {
        List<Rezept> rezeptList = new ArrayList<>();

        //String sql1 = "SELECT r.*, z.BEZEICHNUNG, rz.MENGE, rz.EINHEIT FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR WHERE r.REZEPTNR = (SELECT r.REZEPTNR FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR GROUP BY r.REZEPTNR HAVING SUM((z.KOHLENHYDRATE / 100.0) * rz.MENGE) < :kohlenhydrateLimit);";
        //MapSqlParameterSource params2 = new MapSqlParameterSource("kohlenhydrateLimit", 40);

        rezeptList = template.query(
                sql,
                params,
                new RezeptWithZutatenExtractor()
        );

        return rezeptList;
    }


    public boolean checkZutaten (List<CustomZutat> zutaten) {
        boolean zutatenFound = true;
        String sql = "SELECT CASE WHEN BESTAND >= :menge THEN 'TRUE' ELSE 'FALSE' END AS STATUS FROM ZUTAT WHERE BEZEICHNUNG = :name;";

        for (CustomZutat zutat : zutaten) {

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("menge", zutat.getMenge());
            paramMap.put("name", zutat.getName());

            String string = template.queryForObject(sql, paramMap, String.class);
            if (string.equals("FALSE")) {
                zutatenFound = false;
            }

            paramMap.remove("name");
            paramMap.remove("menge");
        }
        return zutatenFound;
    }



}


