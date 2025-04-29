package org.example.gymbrobox.api;

import org.example.gymbrobox.Service.RecipeService;
import org.example.gymbrobox.model.Recipe;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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



}
