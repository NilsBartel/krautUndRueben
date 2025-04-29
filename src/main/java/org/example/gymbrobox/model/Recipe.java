package org.example.gymbrobox.model;

import java.util.List;

public class Recipe {

    String name;
    String description;
    String preparation;
    double co2;
    double price;
    List<Ingredients> ingredients;
    List<Allergy> allergy;
    List<NutritionCat> nutritionCat;
    List<Nutrients> nutrients;

    public Recipe() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Allergy> getAllergy() {
        return allergy;
    }

    public void setAllergy(List<Allergy> allergy) {
        this.allergy = allergy;
    }

    public List<NutritionCat> getNutritionCat() {
        return nutritionCat;
    }

    public void setNutritionCat(List<NutritionCat> nutritionCat) {
        this.nutritionCat = nutritionCat;
    }

    public List<Nutrients> getNutrients() {
        return nutrients;
    }

    public void setNutrients(List<Nutrients> nutrients) {
        this.nutrients = nutrients;
    }
}
