package org.example.gymbrobox.Service;

import org.example.gymbrobox.NutritionLimit;
import org.example.gymbrobox.database.RecipeRepo;
import org.example.gymbrobox.model.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeService {

    private final RecipeRepo recipeRepo;

    public RecipeService(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }


    public List<Rezept> getRezepte(Map<String, String> queryFilter) {
        Map<String, Object> result = buildSqlRezeptString(queryFilter);

        return recipeRepo.getFilteredRezeptList((String) result.get("sql"), (MapSqlParameterSource) result.get("params"));
    }


    private Map<String, Object> buildSqlRezeptString(Map<String, String> queryFilter) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        Map<String, Object> result = new HashMap<>();

        // String sql = "SELECT r.*, z.BEZEICHNUNG, rz.MENGE, rz.EINHEIT FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR WHERE r.REZEPTNR = (SELECT r.REZEPTNR FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR GROUP BY r.REZEPTNR HAVING SUM((z.CO2 / 1000.0) * rz.MENGE) < 1.7);";
        String sql = "SELECT r.*, z.BEZEICHNUNG, rz.MENGE, rz.EINHEIT FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR WHERE r.REZEPTNR IN (SELECT r.REZEPTNR FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR GROUP BY r.REZEPTNR HAVING ";


        // TODO: check for no filters if it works? can i remove the whole subquery???


        // TODO put the HAVING part

        if (queryFilter.containsKey("ernährungsart")) {
            sql = sql.concat("AND SUM((z.CO2 / 1000.0) * rz.MENGE) < 1.7"); // TODO need to figure out the sql part !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }

        if (queryFilter.containsKey("kohlenhydrate")) {
            if (Objects.equals(queryFilter.get("kohlenhydrate"), "low")) {
                sql = sql.concat("SUM((z.KOHLENHYDRATE / 100.0) * rz.MENGE) < :kohlenhydrateLimit"); // TODO add AND
                params.addValue("kohlenhydrateLimit", NutritionLimit.KOHELENHYDRATE_LOW_LIMIT.getValue());
            } else if (Objects.equals(queryFilter.get("kohlenhydrate"), "high")) {
                sql = sql.concat("SUM((z.KOHLENHYDRATE / 100.0) * rz.MENGE) > :kohlenhydrateLimit"); // TODO add AND
                params.addValue("kohlenhydrateLimit", NutritionLimit.KOHELENHYDRATE_LOW_LIMIT.getValue());
            }
        }

        if (queryFilter.containsKey("protein")) {
            if (Objects.equals(queryFilter.get("protein"), "low")) {
                sql = sql.concat("SUM((z.PROTEIN / 100.0) * rz.MENGE) < :proteinLimit"); // TODO add AND
                params.addValue("proteinLimit", NutritionLimit.PROTEIN_LOW_LIMIT.getValue());
            } else if (Objects.equals(queryFilter.get("protein"), "high")) {
                sql = sql.concat("SUM((z.PROTEIN / 100.0) * rz.MENGE) > :proteinLimit"); // TODO add AND
                params.addValue("proteinLimit", NutritionLimit.PROTEIN_LOW_LIMIT.getValue());
            }
        }

        if (queryFilter.containsKey("fat")) {
            if (Objects.equals(queryFilter.get("fat"), "low")) {
                sql = sql.concat("SUM((z.FETT / 100.0) * rz.MENGE) < :fatLimit"); // TODO add AND
                params.addValue("fatLimit", NutritionLimit.FETT_LOW_LIMIT.getValue());
            } else if (Objects.equals(queryFilter.get("fat"), "high")) {
                sql = sql.concat("SUM((z.FETT / 100.0) * rz.MENGE) > :fatLimit"); // TODO add AND
                params.addValue("fatLimit", NutritionLimit.FETT_LOW_LIMIT.getValue());
            }
        }

        if (queryFilter.containsKey("co2")) {
            if (Objects.equals(queryFilter.get("co2"), "low")) {
                sql = sql.concat("SUM((z.CO2 / 1000.0) * rz.MENGE) < :co2Limit"); // TODO add AND
                params.addValue("co2Limit", NutritionLimit.CO2_LOW_LIMIT.getValue());
            } else if (Objects.equals(queryFilter.get("co2"), "high")) {
                sql = sql.concat("SUM((z.CO2 / 1000.0) * rz.MENGE) > :co2Limit"); // TODO add AND
                params.addValue("co2Limit", NutritionLimit.CO2_LOW_LIMIT.getValue());
            }
        }

        if (queryFilter.containsKey("ingredientLimit")) {
            int limit = Integer.parseInt(queryFilter.get("ingredientLimit"));
        sql = sql.concat("COUNT(DISTINCT rz.ZUTATNR) < :ingredientLimit");    // TODO add AND
            params.addValue("ingredientLimit", limit);
        }

        if (queryFilter.containsKey("amount")) {
            int amount = Integer.parseInt(queryFilter.get("amount"));
            sql = sql.concat(" LIMIT :amount ");        // TODO test this one out more
            params.addValue("amount", amount);
        }

        sql = sql.concat(");");


        result.put("sql", sql);
        result.put("params", params);

        System.out.println("SQL: " + sql);
        System.out.println("Params: " + params);


        return result;

    }














    public Recipe createRecipe () {

        List<Ingredients> ingredients = new ArrayList<>();
        Ingredients ingredient1 = new Ingredients();
        ingredient1.setName("lachs");
        ingredient1.setWeight(1.0);
        ingredient1.setUnit("g");
        ingredient1.setPrice(6.9);
        ingredient1.setQuantity(2);
        ingredients.add(ingredient1);

        Ingredients ingredient2 = new Ingredients();
        ingredient2.setName("milch");
        ingredient2.setWeight(12.8);
        ingredient2.setUnit("ml");
        ingredient2.setPrice(3.1);
        ingredient2.setQuantity(1);
        ingredients.add(ingredient2);

        Ingredients ingredient3 = new Ingredients();
        ingredient3.setName("banane");
        ingredient3.setWeight(1.0);
        ingredient3.setUnit("g");
        ingredient3.setPrice(6.9);
        ingredient3.setQuantity(2);
        ingredients.add(ingredient3);

        List<Allergy> allergies = new ArrayList<>();
        Allergy allergy1 = new Allergy();
        allergy1.setName("Fisch");
        allergy1.setDescription("Fisch allergy");
        allergies.add(allergy1);

        Allergy allergy2 = new Allergy();
        allergy2.setName("Laktose");
        allergy2.setDescription("Laktose allergy");
        allergies.add(allergy2);

        List<NutritionCat> nutritionCats = new ArrayList<>();
        NutritionCat nutritionCat1 = new NutritionCat();
        nutritionCat1.setName("Fleisch essend");
        nutritionCat1.setDescription("Isst Fleisch");
        nutritionCats.add(nutritionCat1);

        NutritionCat nutritionCat2 = new NutritionCat();
        nutritionCat2.setName("High Protein");
        nutritionCat2.setDescription("Viele Proteine");
        nutritionCats.add(nutritionCat2);

        List<Nutrients> nutrients = new ArrayList<>();
        Nutrients nutrient1 = new Nutrients();
        nutrient1.setName("Fett");
        nutrient1.setDescription("Fett gehalt");
        nutrient1.setValue(3.9);
        nutrients.add(nutrient1);

        Nutrients nutrient2 = new Nutrients();
        nutrient2.setName("Protein");
        nutrient2.setDescription("Protein gehalt");
        nutrient2.setValue(31.9);
        nutrients.add(nutrient2);

        Nutrients nutrient3 = new Nutrients();
        nutrient3.setName("Kalorien");
        nutrient3.setDescription("Kalorien gehalt");
        nutrient3.setValue(10);
        nutrients.add(nutrient3);

        Nutrients nutrient4 = new Nutrients();
        nutrient4.setName("Kohlenhydrate");
        nutrient4.setDescription("Kohlenhydrate gehalt");
        nutrient4.setValue(10.8);
        nutrients.add(nutrient4);


        Recipe recipeTest = new Recipe();
        recipeTest.setName("RezeptName");
        recipeTest.setDescription("Dies ist ein Rezept");
        recipeTest.setPreparation("So wird diese rezept gekocht al allalal ala laal ala la alala la alasals alskalsk lasklakslaskalslkas");
        recipeTest.setCo2(2.3);
        recipeTest.setPrice(7.7);
        recipeTest.setIngredients(ingredients);
        recipeTest.setAllergy(allergies);
        recipeTest.setNutritionCat(nutritionCats);
        recipeTest.setNutrients(nutrients);

        return recipeTest;
    }
    
    public void printRecipe (Recipe recipe) {

        System.out.println(recipe.getName());
        System.out.println(recipe.getDescription());
        List<Ingredients> ingredients = recipe.getIngredients();
        for (Ingredients ingredient : ingredients) {
            System.out.println(ingredient.getName() + ", " + ingredient.getWeight() + ", " + ingredient.getUnit() + ", " + ingredient.getPrice() + "€");
        }
        System.out.println(recipe.getPreparation());
        System.out.println("Co2: " + recipe.getCo2());
        System.out.println("Price: " + recipe.getPrice());

        System.out.println();
        System.out.println("Allergies:");
        List<Allergy> allergies = recipe.getAllergy();
        for (Allergy allergy : allergies) {
            System.out.print(allergy.getName() + ", ");
        }

        System.out.println();
        System.out.println("Nutrients:");
        List<Nutrients> nutrients = recipe.getNutrients();
        for (Nutrients nutrient : nutrients) {
            System.out.print(nutrient.getName() + ": " + nutrient.getValue() + ", ");
        }

        System.out.println();
        System.out.println("Nutrition Category:");
        List<NutritionCat> nutritionCats = recipe.getNutritionCat();
        for (NutritionCat nutritionCat : nutritionCats) {
            System.out.print(nutritionCat.getName() + ", ");
        }
    }

}
