package org.example.gymbrobox;

import org.example.gymbrobox.Service.RecipeService;
import org.example.gymbrobox.Service.UserService;
import org.example.gymbrobox.database.RecipeRepo;
import org.example.gymbrobox.database.TestRepository;
import org.example.gymbrobox.database.UserRepo;
import org.example.gymbrobox.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GymBroBoxApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GymBroBoxApplication.class, args);


        TestRepository testRepository = context.getBean(TestRepository.class);
        System.out.println("Kunde: " + testRepository.getKundeCount());
        System.out.println("Lieferant: " + testRepository.getLieferantCount());
        System.out.println();

//        RecipeRepo recipeRepo = context.getBean(RecipeRepo.class);
//        Recipe recipe = recipeRepo.getRecipeByName("lachslasagne");


//        if (recipe != null) {
//            System.out.println(recipe.getName());
//            System.out.println(recipe.getPreparation());
//            List<Ingredients> ingredients = recipe.getIngredients();
//            for (Ingredients ingredient : ingredients) {
//                System.out.println(ingredient.getName() + ", " + ingredient.getWeight() + ", " + ingredient.getUnit() + ", " + ingredient.getPrice() + "€");
//            }
//            System.out.println();
//        } else {
//            System.out.println("empty");
//        }


        RecipeService recipeService = context.getBean(RecipeService.class);
        recipeService.printRecipe(recipeService.createRecipe());


        UserRepo userRepo = context.getBean(UserRepo.class);
        UserService userService = context.getBean(UserService.class);
        User user = new User();
        user = userRepo.findByUsername("wellensteyn");
        userService.printUser(user);





    }

}
