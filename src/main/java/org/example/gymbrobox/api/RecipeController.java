package org.example.gymbrobox.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.gymbrobox.Service.RecipeService;
import org.example.gymbrobox.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;


@RestController
public class RecipeController {

    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of Rezepte",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Rezept.class)))),
            @ApiResponse(responseCode = "404", description = "Not enough recipes found",
                    content = @Content)
    })
    @PostMapping("/recipe/filter")
    @CrossOrigin(origins = "http://localhost:3000")
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

        List<Rezept> rezepte = recipeService.getRezepte(requestBody.toMap());
        if (rezepte.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not enough recipes");
        }
        return rezepte;
    }


    @PostMapping("/recipe/custom")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public String getCustomRecipe(
        @RequestBody List<CustomZutat> zutaten
        //@RequestBody CustomRezept requestBody
    ) {

        if (!recipeService.customZutaten(zutaten)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not enough recipes");
        }


        return "Success";
    }


}
