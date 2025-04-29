package org.example.gymbrobox.database;

import org.example.gymbrobox.database.rowMapper.WholeRecipeRowMapper;
import org.example.gymbrobox.model.Recipe;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeRepo {
    private final NamedParameterJdbcTemplate template;

    public RecipeRepo (NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.template = namedParameterJdbcTemplate;
    }

    public Recipe getRecipeByName(String name) {
        Recipe recipe = new Recipe();

        String sql = "SELECT R.NAME, R.VORGEHEN, Z.BEZEICHNUNG, RZ.MENGE, RZ.EINHEIT, Z.NETTOPREIS FROM REZEPT R JOIN REZEPT_ZUTAT RZ ON R.REZEPTNR = RZ.REZEPTNR JOIN ZUTAT Z ON RZ.ZUTATNR = Z.ZUTATNR WHERE R.NAME = :name;";

        MapSqlParameterSource params = new MapSqlParameterSource("name", name);

//        List<Ingredients> ingredients = template.query (
//                sql, params, new WholeRecipeRwoMapper()
//        );

        recipe = template.query (
                sql, params, new WholeRecipeRowMapper()
        );

//        recipe.setName(name);
//        recipe.setIngredients(ingredients);

        return recipe;
    }

}


