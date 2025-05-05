package org.example.gymbrobox.database;

import org.example.gymbrobox.model.Rezept;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepo {
    private final NamedParameterJdbcTemplate template;
    private final JdbcTemplate jdbcTemplate;
    public OrderRepo(NamedParameterJdbcTemplate template, JdbcTemplate jdbcTemplate) {
        this.template = template;
        this.jdbcTemplate = jdbcTemplate;
    }


    public boolean placeOrder(List<Rezept> rezepte, String username) {

        String sql_bestellNr = "SELECT COALESCE(MAX(BESTELLNR), 0) + 1 AS NEUE_BESTELLNR FROM BESTELLUNG;";
        String sql_bestellung = "INSERT INTO BESTELLUNG (BESTELLNR, KUNDENNR, BESTELLDATUM) SELECT :bestellNr, K.KUNDENNR, CURRENT_DATE() FROM KUNDE K WHERE K.USERNAME = :userName;";
        String sql_bestellung_rezept = "INSERT INTO BESTELLUNG_REZEPT (BESTELLNR, REZEPTNR, PORTIONEN) SELECT :bestellNr, r.REZEPTNR, 1 FROM REZEPT r WHERE NAME = :rezeptName";

        int bestellNr = jdbcTemplate.queryForObject(sql_bestellNr, Integer.class);
        Map<String, Object> params = new HashMap<>();
        params.put("bestellNr", bestellNr);
        params.put("userName", username);

        template.update(sql_bestellung, params);
        params.remove("userName");

        for (Rezept rezept : rezepte) {
            params.put("rezeptName", rezept.getName());
            template.update(sql_bestellung_rezept, params);
            params.remove("rezeptName");
        }

        return true;
    }

}
