package org.example.gymbrobox.Service;

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
    public OrderService(AuthenticationTokenService tokenService) {
        this.tokenService = tokenService;
    }

    public boolean addOrder(BoxRequest boxRequest, String token) {

        String userName = tokenService.getUsername(token);
        System.out.println(userName);

        List<Rezept> allRecipes = new ArrayList<>();
        String customBoxName = "custom";

        for (Box box : boxRequest.getBoxen()) {
            if (!Objects.equals(box.getTyp(), customBoxName)) {
                allRecipes.addAll(box.getRezepte());
            }
        }

        if (allRecipes.isEmpty()) {
            return false;
        }

        // TODO:
        return true;
    }

}
