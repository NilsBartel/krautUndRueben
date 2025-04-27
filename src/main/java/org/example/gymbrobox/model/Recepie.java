package org.example.gymbrobox.model;

import java.util.List;

public class Recepie {

    String name;
    String description;
    String preparation;
    double co2;
    double price;
    List<Ingredients> ingredients;
    List<Allergy> allergy;
    List<NutritionCat> nutritionCat;
    List<Nutrients> nutrients;
}
