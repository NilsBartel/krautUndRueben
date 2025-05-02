package org.example.gymbrobox.database;

import org.example.gymbrobox.model.User;
import org.example.gymbrobox.model.UserAccount;
import org.example.gymbrobox.model.UserAccountWithSecurity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepo {
    private final NamedParameterJdbcTemplate template;

    public LoginRepo (NamedParameterJdbcTemplate template) {
        this.template = template;
    }


    public String getPasswordByUsername (UserAccount userAccount) {
        String sql = "SELECT PASSHASH FROM LOGIN WHERE USERNAME = :username;";

        MapSqlParameterSource params = new MapSqlParameterSource("username", userAccount.getUsername());

        return template.queryForObject(sql, params, String.class);
    }

    public String getSecurityAnswerByUsername (UserAccountWithSecurity userAccount) {
        String sql = "SELECT SECURITYANSWER FROM LOGIN WHERE USERNAME = :username;";

        MapSqlParameterSource params = new MapSqlParameterSource("username", userAccount.getUsername());

        return template.queryForObject(sql, params, String.class);
    }

    public void updatePasswordByUsername (UserAccountWithSecurity userAccount) {
        String sql = "UPDATE LOGIN SET PASSHASH = :password WHERE USERNAME = :username;";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("password", userAccount.getPassword())
                .addValue("username", userAccount.getUsername());
        template.update(sql, params);
    }

    public boolean createUser (UserAccountWithSecurity userAccount, User user) {
        int adressenr;
        int kundennr;
        String sqlAdressenr = "SELECT MAX(ADRESSENR) FROM ADRESSE;";
        String sqlKundennr = "SELECT MAX(KUNDENNR) FROM KUNDE;";
        String sqlLoginInsert = "INSERT INTO LOGIN (USERNAME, PASSHASH, SECURITYANSWER) VALUES (:username, :passHash, :securityAnswer);";
        String sqlAdressInsert = "INSERT INTO ADRESSE (ADRESSENR, STRASSE, HAURSNR, POSTLEITZAHL, ORT, STADT, LAND) VALUES (:adressenr, :street, :houseNumber, :zipCode, :cityDistrict, :city, :country);";
        String sqlKundeInsert = "INSERT INTO KUNDE (KUNDENNR, ADRESSNR, NACHNAME, VORNAME, GEBURTSDATUM, TELEFON, EMAIL, ABO, USERNAME) VALUES (:kundennr, :adressenr, :lastName, :firstName, :birthdate, :phoneNumber, :email, false, :username);";


        try {
            MapSqlParameterSource params = null;
            adressenr = template.queryForObject(sqlAdressenr, params, Integer.class) + 1;
            kundennr = template.queryForObject(sqlKundennr, params, Integer.class) + 1;

            params = new MapSqlParameterSource()
                    .addValue("username", userAccount.getUsername())
                    .addValue("passHash", userAccount.getPassword())
                    .addValue("securityAnswer", userAccount.getSecurityAnswer())
                    .addValue("adressenr", adressenr)
                    .addValue("street", kundennr)
                    .addValue("houseNumber", user.getHouseNumber())
                    .addValue("zipCode", user.getZipCode())
                    .addValue("cityDistrict", user.getCityDistrict())
                    .addValue("city", user.getCity())
                    .addValue("country", user.getCountry())
                    .addValue("kundennr", kundennr)
                    .addValue("lastName", user.getLastName())
                    .addValue("firstName", user.getFirstName())
                    .addValue("birthdate", user.getDateOfBirth())
                    .addValue("phoneNumber", user.getPhoneNumber())
                    .addValue("email", user.getEmail())
            ;

            template.update(sqlLoginInsert, params);
            template.update(sqlAdressInsert, params);
            template.update(sqlKundeInsert, params);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }



}
