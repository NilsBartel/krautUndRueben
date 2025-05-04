package org.example.gymbrobox.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.example.gymbrobox.Service.RecipeService;
import org.example.gymbrobox.model.Recipe;
import org.example.gymbrobox.model.RecipeFilters;
import org.example.gymbrobox.model.Rezept;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/recipe/filter")
    @ResponseBody
    public List<Rezept> getFilteredRecipes(
            //@RequestParam(required = false) Map<String, String> queryParams,
            @RequestBody RecipeFilters requestBody
    ) {


//        System.out.println();
//        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }

        System.out.println();
        System.out.println("requestbody");
        for (Map.Entry<String, String> entry : requestBody.toMap().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }



        //TODO: do the thing (Service, Repo)
        //        List<Rezept> recipes = new ArrayList<>();
//        recipes.add(rezept);
//        recipes.add(recipeService.createRecipe());
//        recipes.add(recipeService.createRecipe());
//        recipes.add(recipeService.createRecipe());


        return recipeService.getRezepte(requestBody.toMap());

    }


}
