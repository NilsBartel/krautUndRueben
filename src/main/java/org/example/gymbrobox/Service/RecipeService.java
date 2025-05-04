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
        boolean addedFilter = false;

        // old one
        //String sql = "SELECT r.*, z.BEZEICHNUNG, rz.MENGE, rz.EINHEIT FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR WHERE r.REZEPTNR IN (SELECT r.REZEPTNR FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR GROUP BY r.REZEPTNR HAVING ";
        // has the subquery for LIMIT and IN
        //String sql = "SELECT r.*, z.BEZEICHNUNG, rz.MENGE, rz.EINHEIT FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR WHERE r.REZEPTNR IN (SELECT REZEPTNR FROM (SELECT r.REZEPTNR FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR GROUP BY r.REZEPTNR ";
        String sql = "SELECT r.*, z.BEZEICHNUNG, rz.MENGE, rz.EINHEIT FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR ";


        // TODO: check for no filters if it works? can i remove the whole subquery???


        // TODO put the HAVING part

        if (queryFilter.containsKey("ernährungsart")) {
            String ernaehrungsString = "WHERE NOT EXISTS (SELECT 1 FROM REZEPT_ZUTAT rz WHERE rz.REZEPTNR = r.REZEPTNR AND rz.ZUTATNR NOT IN (select z.ZUTATNR from ZUTAT z join ZUTAT_ERNAEHRUNGSKATEGORIE ze on z.ZUTATNR = ze.ZUTATNR where ze.ERNAEHRUNGSKATNR in (SELECT e.ERNAEHRUNGSKATNR FROM ERNAEHRUNGSKATEGORIE e WHERE e.PRIORITAET >= (SELECT e2.PRIORITAET FROM ERNAEHRUNGSKATEGORIE e2 WHERE e2.BEZEICHNUNG = :ernährungsart) AND e.TYP = 'ernährungsart')))";

            if (Objects.equals(queryFilter.get("ernährungsart"), "vegan")) {
                sql = sql.concat(ernaehrungsString);
                params.addValue("ernährungsart", "vegan");

            } else if (Objects.equals(queryFilter.get("ernährungsart"), "vegetarisch")) {
                sql = sql.concat(ernaehrungsString);
                params.addValue("ernährungsart", "vegetarisch");

            } else if (Objects.equals(queryFilter.get("ernährungsart"), "mischKost")) {
                sql = sql.concat(ernaehrungsString);
                params.addValue("ernährungsart", "fleisch essend");

            } else if (Objects.equals(queryFilter.get("ernährungsart"), "fruitarisch")) {
                sql = sql.concat(ernaehrungsString);
                params.addValue("ernährungsart", "fruitarisch");

            }

            // TODO need to figure out the sql part !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }






        sql = sql.concat(" AND r.REZEPTNR IN (SELECT REZEPTNR FROM (SELECT r.REZEPTNR FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR GROUP BY r.REZEPTNR ");




        if (queryFilter.containsKey("kohlenhydrate")) {
            if (Objects.equals(queryFilter.get("kohlenhydrate"), "low")) {
                if (addedFilter) {
                    sql = sql.concat(" AND ");
                } else {
                    sql = sql.concat(" HAVING ");
                }
                sql = sql.concat("SUM((z.KOHLENHYDRATE / 100.0) * rz.MENGE) < :kohlenhydrateLimit");
                params.addValue("kohlenhydrateLimit", NutritionLimit.KOHELENHYDRATE_LOW_LIMIT.getValue());
                addedFilter = true;
            } else if (Objects.equals(queryFilter.get("kohlenhydrate"), "high")) {
                if (addedFilter) {
                    sql = sql.concat(" AND ");
                } else {
                    sql = sql.concat(" HAVING ");
                }
                sql = sql.concat("SUM((z.KOHLENHYDRATE / 100.0) * rz.MENGE) > :kohlenhydrateLimit");
                params.addValue("kohlenhydrateLimit", NutritionLimit.KOHELENHYDRATE_LOW_LIMIT.getValue());
                addedFilter = true;
            }
        }

        if (queryFilter.containsKey("protein")) {
            if (Objects.equals(queryFilter.get("protein"), "low")) {
                if (addedFilter) {
                    sql = sql.concat(" AND ");
                } else {
                    sql = sql.concat(" HAVING ");
                }
                sql = sql.concat("SUM((z.PROTEIN / 100.0) * rz.MENGE) < :proteinLimit");
                params.addValue("proteinLimit", NutritionLimit.PROTEIN_LOW_LIMIT.getValue());
                addedFilter = true;
            } else if (Objects.equals(queryFilter.get("protein"), "high")) {
                if (addedFilter) {
                    sql = sql.concat(" AND ");
                } else {
                    sql = sql.concat(" HAVING ");
                }
                sql = sql.concat("SUM((z.PROTEIN / 100.0) * rz.MENGE) > :proteinLimit");
                params.addValue("proteinLimit", NutritionLimit.PROTEIN_LOW_LIMIT.getValue());
                addedFilter = true;
            }
        }

        if (queryFilter.containsKey("fat")) {
            if (Objects.equals(queryFilter.get("fat"), "low")) {
                if (addedFilter) {
                    sql = sql.concat(" AND ");
                } else {
                    sql = sql.concat(" HAVING ");
                }
                sql = sql.concat("SUM((z.FETT / 100.0) * rz.MENGE) < :fatLimit");
                params.addValue("fatLimit", NutritionLimit.FETT_LOW_LIMIT.getValue());
                addedFilter = true;
            } else if (Objects.equals(queryFilter.get("fat"), "high")) {
                if (addedFilter) {
                    sql = sql.concat(" AND ");
                } else {
                    sql = sql.concat(" HAVING ");
                }
                sql = sql.concat("SUM((z.FETT / 100.0) * rz.MENGE) > :fatLimit");
                params.addValue("fatLimit", NutritionLimit.FETT_LOW_LIMIT.getValue());
                addedFilter = true;
            }
        }

        if (queryFilter.containsKey("co2")) {
            if (Objects.equals(queryFilter.get("co2"), "low")) {
                if (addedFilter) {
                    sql = sql.concat(" AND ");
                } else {
                    sql = sql.concat(" HAVING ");
                }
                sql = sql.concat("SUM((z.CO2 / 1000.0) * rz.MENGE) < :co2Limit");
                params.addValue("co2Limit", NutritionLimit.CO2_LOW_LIMIT.getValue());
                addedFilter = true;
            } else if (Objects.equals(queryFilter.get("co2"), "high")) {
                if (addedFilter) {
                    sql = sql.concat(" AND ");
                } else {
                    sql = sql.concat(" HAVING ");
                }
                sql = sql.concat("SUM((z.CO2 / 1000.0) * rz.MENGE) > :co2Limit");
                params.addValue("co2Limit", NutritionLimit.CO2_LOW_LIMIT.getValue());
                addedFilter = true;
            }
        }

        if (queryFilter.containsKey("ingredientLimit")) {
            if (addedFilter) {
                sql = sql.concat(" AND ");
            } else {
                sql = sql.concat(" HAVING ");
            }
            int limit = Integer.parseInt(queryFilter.get("ingredientLimit"));
            sql = sql.concat("COUNT(DISTINCT rz.ZUTATNR) < :ingredientLimit");
            params.addValue("ingredientLimit", limit);
            addedFilter = true;
        }

        if (queryFilter.containsKey("amount")) {
            int amount = Integer.parseInt(queryFilter.get("amount"));
            sql = sql.concat(" LIMIT :amount ");        // TODO test this one out more
            params.addValue("amount", amount);
        }

        if (addedFilter) {
            sql = sql.concat(") AS LIMIT_REZEPTE)");
        }

        sql = sql.concat(";");



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
