package org.example.gymbrobox.model;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;
import java.util.Map;

public class RecipeFilters {
    @Schema(name = "ernährungsart", description = "frutarisch, vegan, vegetarian, mischKost ", example = "vegan")
    private String ernährungsart;

    @Schema(name = "kohlenhydrate", description = "low, high, all", example = "low")
    private String kohlenhydrate;

    @Schema(name = "kalorien", description = "low, high, all", example = "low")
    private String kalorien;

    @Schema(name = "ingredientLimit", description = "int, as number of ingredients", example = "12")
    private String ingredientLimit;

    @Schema(name = "protein", description = "low, high, all", example = "high")
    private String protein;

    @Schema(name = "amount", description = "int, as number of recipes", example = "2")
    private String amount;

    @Schema(name = "fat", description = "low, high, all", example = "low")
    private String fat;

    @Schema(name = "co2", description = "low, high, all", example = "low")
    private String co2;


    public RecipeFilters() {
    }

    public String getErnährungsart() {
        return ernährungsart;
    }

    public void setErnährungsart(String ernährungsart) {
        this.ernährungsart = ernährungsart;
    }

    public String getKohlenhydrate() {
        return kohlenhydrate;
    }

    public void setKohlenhydrate(String kohlenhydrate) {
        this.kohlenhydrate = kohlenhydrate;
    }

    public String getIngredientLimit() {
        return ingredientLimit;
    }

    public void setIngredientLimit(String ingredientLimit) {
        this.ingredientLimit = ingredientLimit;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCo2() {
        return co2;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }

    public String getKalorien() {
        return kalorien;
    }

    public void setKalorien(String kalorien) {
        this.kalorien = kalorien;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("ernährungsart", ernährungsart);
        map.put("kohlenhydrate", kohlenhydrate);
        map.put("kalorien", kalorien);
        map.put("ingredientLimit", ingredientLimit);
        map.put("protein", protein);
        map.put("amount", amount);
        map.put("fat", fat);
        map.put("co2", co2);

        return map;
    }
}
