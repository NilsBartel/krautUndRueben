package org.example.gymbrobox.model;

import java.util.HashMap;
import java.util.Map;

public class RecipeFilters {
    private String ernährungsart;
    private String kohlenhydrate;
    private String ingredientLimit;
    private String protein;
    private String amount;
    private String fat;
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

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("ernährungsart", ernährungsart);
        map.put("kohlenhydrate", kohlenhydrate);
        map.put("ingredientLimit", ingredientLimit);
        map.put("protein", protein);
        map.put("amount", amount);
        map.put("fat", fat);
        map.put("co2", co2);

        return map;
    }
}
