package org.example.gymbrobox.database.rowMapper;

import org.example.gymbrobox.model.Ingredients;
import org.example.gymbrobox.model.Recipe;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class WholeRecipeRowMapper implements ResultSetExtractor<Recipe> {


        private final RowMapper<Ingredients> ingredientRowMapper = new IngredientRowMapper();
        private final RowMapper<Recipe> recipeRowMapper = new RecipieRowMapper();

        @Override
        public Recipe extractData(ResultSet rs) throws SQLException {
            Recipe recipe = null;
            List<Ingredients> ingredients = new ArrayList<>();

            while (rs.next()) {
                // Map Recipe (if not already done)
                if (recipe == null) {
                    recipe = recipeRowMapper.mapRow(rs, rs.getRow());
                }

                // Map Ingredients
                Ingredients ingredient = ingredientRowMapper.mapRow(rs, rs.getRow());
                ingredients.add(ingredient);
            }

            if (recipe != null) {
                recipe.setIngredients(ingredients);
            }

            return recipe;
        }

}
