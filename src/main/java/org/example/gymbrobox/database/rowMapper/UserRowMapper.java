package org.example.gymbrobox.database.rowMapper;

import org.example.gymbrobox.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserName(rs.getString("USERNAME"));
        user.setFirstName(rs.getString("VORNAME"));
        user.setLastName(rs.getString("NACHNAME"));
        user.setEmail(rs.getString("EMAIL"));
        user.setPhoneNumber(rs.getString("TELEFON"));
        user.setDateOfBirth(rs.getDate("GEBURTSDATUM"));
        user.setAbo(rs.getBoolean("ABO"));
        user.setStreet(rs.getString("STRASSE"));
        user.setHouseNumber(rs.getString("HAURSNR"));
        user.setPlz(rs.getString("POSTLEITZAHL"));
        user.setCity(rs.getString("STADT"));
        user.setCountry(rs.getString("LAND"));

        return user;
    }
}
