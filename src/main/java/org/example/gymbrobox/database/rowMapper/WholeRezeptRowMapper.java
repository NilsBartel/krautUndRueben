package org.example.gymbrobox.database.rowMapper;

import org.example.gymbrobox.model.Rezept;
import org.example.gymbrobox.model.Zutat;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WholeRezeptRowMapper implements ResultSetExtractor<Rezept> {

    private final RowMapper<Rezept> rezeptRowMapper = new RezeptRowMapper();
    private final RowMapper<Zutat> zutatRowMapper = new ZutatRowMapper();


    @Override
    public Rezept extractData(ResultSet resultSet) throws SQLException {
        Rezept rezept = null;
        List<Zutat> zutaten = new ArrayList<>();

        while (resultSet.next()) {
            if (rezept == null) {
                rezept = rezeptRowMapper.mapRow(resultSet, resultSet.getRow());
            }

            Zutat zutat = zutatRowMapper.mapRow(resultSet, resultSet.getRow());
            zutaten.add(zutat);
        }

        if (rezept != null) {
            rezept.setZutaten(zutaten);
        }
        return rezept;
    }

}


//public class WholeRecipeRowMapper implements ResultSetExtractor<Recipe> {
//
//
//    private final RowMapper<Ingredients> ingredientRowMapper = new IngredientRowMapper();
//    private final RowMapper<Recipe> recipeRowMapper = new RecipieRowMapper();
//
//    @Override
//    public Recipe extractData(ResultSet rs) throws SQLException {
//        Recipe recipe = null;
//        List<Ingredients> ingredients = new ArrayList<>();
//
//        while (rs.next()) {
//            // Map Recipe (if not already done)
//            if (recipe == null) {
//                recipe = recipeRowMapper.mapRow(rs, rs.getRow());
//            }
//
//            // Map Ingredients
//            Ingredients ingredient = ingredientRowMapper.mapRow(rs, rs.getRow());
//            ingredients.add(ingredient);
//        }
//
//        if (recipe != null) {
//            recipe.setIngredients(ingredients);
//        }
//
//        return recipe;
//    }
//
//}
