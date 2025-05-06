package org.example.gymbrobox.Service;

import org.example.gymbrobox.database.OrderRepo;
import org.example.gymbrobox.model.Box;
import org.example.gymbrobox.model.BoxRequest;
import org.example.gymbrobox.model.Rezept;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final AuthenticationTokenService tokenService;
    private final OrderRepo orderRepo;
    public OrderService(AuthenticationTokenService tokenService, OrderRepo orderRepo) {
        this.tokenService = tokenService;
        this.orderRepo = orderRepo;
    }

    public boolean addOrder(BoxRequest boxRequest, String token) {

        String userName = tokenService.getUsername(token);

        List<String> allRecipes = new ArrayList<>();
        String customBoxName = "custom";

        for (Box box : boxRequest.getBoxen()) {
            if (!Objects.equals(box.getTyp(), customBoxName)) {
                allRecipes.addAll(box.getRezepte());
            }
        }

        if (allRecipes.isEmpty()) {
            return false;
        }



        return orderRepo.placeOrder(createRecipeMap(allRecipes), userName);
    }

    private Map<String, Integer> createRecipeMap(List<String> recipes) {

        Map<String, Integer> map = new HashMap<>();

        for (String recipe : recipes) {
            if (!map.containsKey(recipe)) {
                map.put(recipe, 1);
            }
            else {
                map.put(recipe, map.get(recipe) + 1);
            }
        }
        return map;
    }

}
