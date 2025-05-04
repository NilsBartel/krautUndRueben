package org.example.gymbrobox.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.example.gymbrobox.Service.RecipeService;
import org.example.gymbrobox.model.Recipe;
import org.example.gymbrobox.model.Rezept;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class RecipeController {

    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping("/recipe")
    @ResponseBody
    public Recipe getRecipeByName(@RequestParam String name) {
        return recipeService.createRecipe();
    }

    @Parameters({
            @Parameter(name = "ernährungsart", description = "frutarisch, vegan, vegetarian, mischKost ", example = "vegan"),
            @Parameter(name = "kohlenhydrate", description = "low, high", example = "low"),
            @Parameter(name = "kalorien", description = "low, high", example = "low"),
            @Parameter(name = "fett", description = "low, high", example = "low"),
            @Parameter(name = "protein", description = "low, high", example = "high"),
            @Parameter(name = "anzahlRezepte", description = "int, as number of recipes", example = "1"),
            @Parameter(name = "anzahlZutaten", description = "int, as number of ingredients", example = "1")
    })
    @GetMapping("/recipe/filter")
    @ResponseBody
    public List<Rezept> getFilteredRecipes(
            @RequestParam(required = false) Map<String, String> queryParams
    ) {


        System.out.println();
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }


        //TODO: do the thing (Service, Repo)
        List<Rezept> rezept = recipeService.getRezepte(queryParams);
//        List<Rezept> recipes = new ArrayList<>();
//        recipes.add(rezept);
//        recipes.add(recipeService.createRecipe());
//        recipes.add(recipeService.createRecipe());
//        recipes.add(recipeService.createRecipe());


        return rezept;

    }


}
