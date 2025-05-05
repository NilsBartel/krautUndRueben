package org.example.gymbrobox.Service;

import org.example.gymbrobox.NutritionLimit;
import org.example.gymbrobox.database.RecipeRepo;
import org.example.gymbrobox.model.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepo recipeRepo;

    public RecipeService(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }


    public boolean customZutaten(List<CustomZutat> zutaten) {
        return recipeRepo.checkZutaten(zutaten);
    }


    public List<Rezept> getRezepte(Map<String, String> queryFilter) {
        Map<String, Object> result = buildSqlRezeptString(queryFilter);

        List<Rezept> rezepte = recipeRepo.getFilteredRezeptList((String) result.get("sql"), (MapSqlParameterSource) result.get("params"));






        if (queryFilter.containsKey("amount")) {
            if (rezepte.size() < Integer.parseInt(queryFilter.get("amount"))) {
                return Collections.emptyList();
            }

            // TODO: add rezepte to order

            return chooseRandomRezepte(Integer.parseInt(queryFilter.get("amount")), rezepte);
        }

        return rezepte;
    }

    private boolean addRezepteToOrder(List<Rezept> rezepte) {

    }

    private List<Rezept> chooseRandomRezepte(int amount, List<Rezept> rezepte) {
        List<Rezept> newRezeptList;
        //TODO: cheated

        newRezeptList = rezepte.stream().limit(amount).collect(Collectors.toList());


        return newRezeptList;
    }


    private Map<String, Object> buildSqlRezeptString(Map<String, String> queryFilter) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        Map<String, Object> result = new HashMap<>();
        boolean addedFilter = false;

        // old one
        //String sql = "SELECT r.*, z.BEZEICHNUNG, rz.MENGE, rz.EINHEIT FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR WHERE r.REZEPTNR IN (SELECT r.REZEPTNR FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR GROUP BY r.REZEPTNR HAVING ";
        // has the subquery for LIMIT and IN
        //String sql = "SELECT r.*, z.BEZEICHNUNG, rz.MENGE, rz.EINHEIT FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR WHERE r.REZEPTNR IN (SELECT REZEPTNR FROM (SELECT r.REZEPTNR FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR GROUP BY r.REZEPTNR ";
        String sql = "SELECT r.*, z.BEZEICHNUNG, rz.MENGE, rz.EINHEIT FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR ";


        // TODO: check for no filters if it works? can i remove the whole subquery???


        // TODO put the HAVING part

        if (queryFilter.containsKey("ernährungsart")) {
            String ernaehrungsString = "WHERE NOT EXISTS (SELECT 1 FROM REZEPT_ZUTAT rz WHERE rz.REZEPTNR = r.REZEPTNR AND rz.ZUTATNR NOT IN (select z.ZUTATNR from ZUTAT z join ZUTAT_ERNAEHRUNGSKATEGORIE ze on z.ZUTATNR = ze.ZUTATNR where ze.ERNAEHRUNGSKATNR in (SELECT e.ERNAEHRUNGSKATNR FROM ERNAEHRUNGSKATEGORIE e WHERE e.PRIORITAET >= (SELECT e2.PRIORITAET FROM ERNAEHRUNGSKATEGORIE e2 WHERE e2.BEZEICHNUNG = :ernährungsart) AND e.TYP = 'ernährungsart')))";

            if (Objects.equals(queryFilter.get("ernährungsart"), "vegan")) {
                sql = sql.concat(ernaehrungsString);
                params.addValue("ernährungsart", "vegan");

            } else if (Objects.equals(queryFilter.get("ernährungsart"), "vegetarisch")) {
                sql = sql.concat(ernaehrungsString);
                params.addValue("ernährungsart", "vegetarisch");

            } else if (Objects.equals(queryFilter.get("ernährungsart"), "mischKost")) {
                sql = sql.concat(ernaehrungsString);
                params.addValue("ernährungsart", "fleisch essend");

            } else if (Objects.equals(queryFilter.get("ernährungsart"), "frutarisch")) {
                sql = sql.concat(ernaehrungsString);
                params.addValue("ernährungsart", "frutarisch");

            }

            // TODO need to figure out the sql part !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }






        //sql = sql.concat(" AND r.REZEPTNR IN (SELECT REZEPTNR FROM (SELECT r.REZEPTNR FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR GROUP BY r.REZEPTNR ");
        sql = sql.concat(" AND r.REZEPTNR IN (SELECT r.REZEPTNR FROM REZEPT r JOIN REZEPT_ZUTAT rz ON r.REZEPTNR = rz.REZEPTNR JOIN ZUTAT z ON rz.ZUTATNR = z.ZUTATNR GROUP BY r.REZEPTNR ");




        if (queryFilter.containsKey("kohlenhydrate")) {
            if (Objects.equals(queryFilter.get("kohlenhydrate"), "low")) {
                if (addedFilter) {
                    sql = sql.concat(" AND ");
                } else {
                    sql = sql.concat(" HAVING ");
                }
                sql = sql.concat("SUM((z.KOHLENHYDRATE / 100.0) * rz.MENGE) < :kohlenhydrateLimit");
                params.addValue("kohlenhydrateLimit", NutritionLimit.KOHELENHYDRATE_LOW_LIMIT.getValue());
                addedFilter = true;
            } else if (Objects.equals(queryFilter.get("kohlenhydrate"), "high")) {
                if (addedFilter) {
                    sql = sql.concat(" AND ");
                } else {
                    sql = sql.concat(" HAVING ");
                }
                sql = sql.concat("SUM((z.KOHLENHYDRATE / 100.0) * rz.MENGE) > :kohlenhydrateLimit");
                params.addValue("kohlenhydrateLimit", NutritionLimit.KOHELENHYDRATE_LOW_LIMIT.getValue());
                addedFilter = true;
            }
        }

        if (queryFilter.containsKey("protein")) {
            if (Objects.equals(queryFilter.get("protein"), "low")) {
                if (addedFilter) {
                    sql = sql.concat(" AND ");
                } else {
                    sql = sql.concat(" HAVING ");
                }
                sql = sql.concat("SUM((z.PROTEIN / 100.0) * rz.MENGE) < :proteinLimit");
                params.addValue("proteinLimit", NutritionLimit.PROTEIN_LOW_LIMIT.getValue());
                addedFilter = true;
            } else if (Objects.equals(queryFilter.get("protein"), "high")) {
                if (addedFilter) {
                    sql = sql.concat(" AND ");
                } else {
                    sql = sql.concat(" HAVING ");
                }
                sql = sql.concat("SUM((z.PROTEIN / 100.0) * rz.MENGE) > :proteinLimit");
                params.addValue("proteinLimit", NutritionLimit.PROTEIN_LOW_LIMIT.getValue());
                addedFilter = true;
            }
        }

        if (queryFilter.containsKey("fat")) {
            if (Objects.equals(queryFilter.get("fat"), "low")) {
                if (addedFilter) {
                    sql = sql.concat(" AND ");
                } else {
                    sql = sql.concat(" HAVING ");
                }
                sql = sql.concat("SUM((z.FETT / 100.0) * rz.MENGE) < :fatLimit");
                params.addValue("fatLimit", NutritionLimit.FETT_LOW_LIMIT.getValue());
                addedFilter = true;
            } else if (Objects.equals(queryFilter.get("fat"), "high")) {
                if (addedFilter) {
                    sql = sql.concat(" AND ");
                } else {
                    sql = sql.concat(" HAVING ");
                }
                sql = sql.concat("SUM((z.FETT / 100.0) * rz.MENGE) > :fatLimit");
                params.addValue("fatLimit", NutritionLimit.FETT_LOW_LIMIT.getValue());
                addedFilter = true;
            }
        }

        if (queryFilter.containsKey("co2")) {
            if (Objects.equals(queryFilter.get("co2"), "low")) {
                if (addedFilter) {
                    sql = sql.concat(" AND ");
                } else {
                    sql = sql.concat(" HAVING ");
                }
                sql = sql.concat("SUM((z.CO2 / 1000.0) * rz.MENGE) < :co2Limit");
                params.addValue("co2Limit", NutritionLimit.CO2_LOW_LIMIT.getValue());
                addedFilter = true;
            } else if (Objects.equals(queryFilter.get("co2"), "high")) {
                if (addedFilter) {
                    sql = sql.concat(" AND ");
                } else {
                    sql = sql.concat(" HAVING ");
                }
                sql = sql.concat("SUM((z.CO2 / 1000.0) * rz.MENGE) > :co2Limit");
                params.addValue("co2Limit", NutritionLimit.CO2_LOW_LIMIT.getValue());
                addedFilter = true;
            }
        }

        if (queryFilter.containsKey("ingredientLimit")) {
            if (addedFilter) {
                sql = sql.concat(" AND ");
            } else {
                sql = sql.concat(" HAVING ");
            }
            int limit = Integer.parseInt(queryFilter.get("ingredientLimit"));
            sql = sql.concat("COUNT(DISTINCT rz.ZUTATNR) < :ingredientLimit");
            params.addValue("ingredientLimit", limit);
            addedFilter = true;
        }

//        if (queryFilter.containsKey("amount")) {
//            int amount = Integer.parseInt(queryFilter.get("amount"));
//            sql = sql.concat(" LIMIT :amount ");        // TODO test this one out more
//            params.addValue("amount", amount);
//        }

//        if (addedFilter) {
//            sql = sql.concat(") AS LIMIT_REZEPTE)");
//        }

        sql = sql.concat(");");



        result.put("sql", sql);
        result.put("params", params);

        System.out.println("SQL: " + sql);
        System.out.println("Params: " + params);


        return result;

    }



}
