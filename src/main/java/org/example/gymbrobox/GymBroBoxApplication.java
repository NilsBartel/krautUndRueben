package org.example.gymbrobox;

import org.example.gymbrobox.database.RecipeRepo;
import org.example.gymbrobox.database.TestRepository;
import org.example.gymbrobox.model.Ingredients;
import org.example.gymbrobox.model.Recipie;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class GymBroBoxApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GymBroBoxApplication.class, args);


        TestRepository testRepository = context.getBean(TestRepository.class);
        System.out.println("Kunde: " + testRepository.getKundeCount());
        System.out.println("Lieferant: " + testRepository.getLieferantCount());

        RecipeRepo recipeRepo = context.getBean(RecipeRepo.class);
        Recipie recipie = recipeRepo.getRecipeByName("test");

        System.out.println(recipie.getName());
        List<Ingredients> ingredients = recipie.getIngredients();
        for (Ingredients ingredient : ingredients) {
            System.out.println(ingredient.getName() + ", " + ingredient.getWeight() + ", " + ingredient.getUnit());
        }
        System.out.println();


    }

}
