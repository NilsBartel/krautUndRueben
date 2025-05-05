package org.example.gymbrobox.Service;

import org.example.gymbrobox.database.OrderRepo;
import org.example.gymbrobox.model.Box;
import org.example.gymbrobox.model.BoxRequest;
import org.example.gymbrobox.model.Rezept;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        return orderRepo.placeOrder(allRecipes, userName);
    }

}
