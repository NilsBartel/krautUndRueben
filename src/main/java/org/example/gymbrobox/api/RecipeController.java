package org.example.gymbrobox.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.example.gymbrobox.Service.RecipeService;
import org.example.gymbrobox.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
public class RecipeController {

    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @PostMapping("/recipe/filter")
    @ResponseBody
    public List<Rezept> getFilteredRecipes(
            @RequestBody RecipeFilters requestBody
    ) {

        System.out.println();
        System.out.println("requestbody");
        for (Map.Entry<String, String> entry : requestBody.toMap().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }


        //TODO: if query successful do add to bestellung


        return recipeService.getRezepte(requestBody.toMap());

    }


    @PostMapping("/recipe/custom")
    @ResponseBody
    public Rezept getCustomRecipe(
        @RequestBody CustomRezept requestBody
    ) {


        System.out.println(requestBody.getName());
        System.out.println(requestBody.getPortionen() + " Portionen");
        System.out.println("Zutaten:");
        for (Zutat zutat : requestBody.getZutaten()) {
            System.out.println(zutat.getName() + " " + (int) zutat.getMenge() + " " + zutat.getEinheit());
        }






        return new Rezept();
    }


}
