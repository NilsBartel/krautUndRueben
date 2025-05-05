package org.example.gymbrobox.api;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.gymbrobox.Service.AuthenticationTokenService;
import org.example.gymbrobox.Service.OrderService;
import org.example.gymbrobox.Service.RecipeService;
import org.example.gymbrobox.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@RestController
public class RecipeController {

    private final RecipeService recipeService;
    private final AuthenticationTokenService tokenService;
    private final OrderService orderService;
    public RecipeController(RecipeService recipeService, AuthenticationTokenService tokenService, OrderService orderService) {
        this.recipeService = recipeService;
        this.tokenService = tokenService;
        this.orderService = orderService;
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
            @RequestBody RecipeFilters requestBody,
            @RequestHeader(value = "token", required = false) String token
    ) {

        if (!tokenService.authenticate(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token wrong");
        }

//        System.out.println();
//        System.out.println("requestbody");
//        for (Map.Entry<String, String> entry : requestBody.toMap().entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }


        //TODO: if query successful do add to bestellung

        List<Rezept> rezepte = recipeService.getRezepte(requestBody.toMap());




        if (rezepte.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not enough recipes");
        }
        return rezepte;
    }



    @PostMapping("/recipe/order")
    @CrossOrigin(origins = "http://localhost:3000")
    public Map<String, String> orderRecipe(
            @RequestBody BoxRequest boxRequest,
            @RequestHeader(value = "token", required = false) String token
    ) {

        if (!tokenService.authenticate(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token wrong");
        }

        orderService.addOrder(boxRequest, token);



        return Map.of("status", "success");
    }


    @PostMapping("/recipe/custom")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public Map<String, String>  getCustomRecipe(
        @RequestBody List<CustomZutat> zutaten
        //@RequestBody CustomRezept requestBody
    ) {

        if (!recipeService.customZutaten(zutaten)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not enough recipes");
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("status", "success");


      return map;
    }


}
