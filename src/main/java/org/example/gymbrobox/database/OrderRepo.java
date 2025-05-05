package org.example.gymbrobox.database;

import org.example.gymbrobox.model.Rezept;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepo {
    private final NamedParameterJdbcTemplate template;
    public OrderRepo(NamedParameterJdbcTemplate template) {
        this.template = template;
    }


    public boolean placeOrder(List<Rezept> rezepte, String username) {




        return true; // TODO:
    }

}
