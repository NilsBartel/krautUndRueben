package org.example.gymbrobox.database;

import org.example.gymbrobox.database.rowMapper.RezeptWithZutatenExtractor;
import org.example.gymbrobox.model.Rezept;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RecipeRepo {
    private final NamedParameterJdbcTemplate template;

    public RecipeRepo (NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.template = namedParameterJdbcTemplate;
    }

//    public Recipe getRecipeByName(String name) {
//        Recipe recipe = new Recipe();
//
//        String sql = "SELECT R.NAME, R.VORGEHEN, Z.BEZEICHNUNG, RZ.MENGE, RZ.EINHEIT, Z.NETTOPREIS FROM REZEPT R JOIN REZEPT_ZUTAT RZ ON R.REZEPTNR = RZ.REZEPTNR JOIN ZUTAT Z ON RZ.ZUTATNR = Z.ZUTATNR WHERE R.NAME = :name;";
//
//        MapSqlParameterSource params = new MapSqlParameterSource("name", name);
//
////        List<Ingredients> ingredients = template.query (
////                sql, params, new WholeRecipeRwoMapper()
////        );
//
//        recipe = template.query (
//                sql, params, new WholeRecipeRowMapper()
//        );
//
////        recipe.setName(name);
////        recipe.setIngredients(ingredients);
//
//        return recipe;
//    }


    public List<Rezept> getFilteredRezeptList (String sql, MapSqlParameterSource params) {
        List<Rezept> rezeptList = new ArrayList<>();

        //String sql1 = "SELECT r.*, z.BEZEICHNUNG, rz.MENGE, rz.EINHEIT FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR WHERE r.REZEPTNR = (SELECT r.REZEPTNR FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR GROUP BY r.REZEPTNR HAVING SUM((z.KOHLENHYDRATE / 100.0) * rz.MENGE) < :kohlenhydrateLimit);";

        //MapSqlParameterSource params2 = new MapSqlParameterSource("kohlenhydrateLimit", 40);

        rezeptList = template.query(
                sql,
                params,
                new RezeptWithZutatenExtractor()
                //new WholeRezeptRowMapper()
        );



        return rezeptList;
    }

}


