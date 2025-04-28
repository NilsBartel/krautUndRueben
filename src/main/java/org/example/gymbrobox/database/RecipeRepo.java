package org.example.gymbrobox.database;

import org.example.gymbrobox.database.rowMapper.IngredientRowMapper;
import org.example.gymbrobox.model.Ingredients;
import org.example.gymbrobox.model.Recipie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class RecipeRepo {
    private final NamedParameterJdbcTemplate template;

    public RecipeRepo (NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.template = namedParameterJdbcTemplate;

    }

    public Recipie getRecipeByName(String name) {
        Recipie recipie = new Recipie();

        String sql = "SELECT R.NAME, Z.BEZEICHNUNG, RZ.GEWICHT, RZ.EINHEIT, Z.NETTOPREIS FROM REZEPT R JOIN REZEPT_ZUTAT RZ ON R.REZEPTNR = RZ.REZEPTNR JOIN ZUTAT Z ON RZ.ZUTATNR = Z.ZUTATNR WHERE R.NAME = :name;";

        MapSqlParameterSource params = new MapSqlParameterSource("name", name);

        List<Ingredients> ingredients = template.query (
                sql, params, new IngredientRowMapper()
        );

        recipie.setName(name);
        recipie.setIngredients(ingredients);

        return recipie;
    }

}


