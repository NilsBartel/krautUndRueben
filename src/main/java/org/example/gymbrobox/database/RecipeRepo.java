package org.example.gymbrobox.database;

import org.example.gymbrobox.database.rowMapper.IngredientRowMapper;
import org.example.gymbrobox.model.Ingredients;
import org.example.gymbrobox.model.Recipie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeRepo {
    private final JdbcTemplate template;

    public RecipeRepo(JdbcTemplate template) {
        this.template = template;

    }

    public Recipie getRecipeByName(String name) {
        Recipie recipie = new Recipie();
        List<Ingredients> ingredients = template.query (
                "SELECT R.NAME, Z.BEZEICHNUNG, RZ.GEWICHT, RZ.EINHEIT, Z.NETTOPREIS FROM REZEPT R JOIN REZEPT_ZUTAT RZ ON R.REZEPTNR = RZ.REZEPTNR JOIN ZUTAT Z ON RZ.ZUTATNR = Z.ZUTATNR WHERE R.REZEPTNR = 1;", new IngredientRowMapper()
        );

        recipie.setName(name);
        recipie.setIngredients(ingredients);

        return recipie;
    }

}


