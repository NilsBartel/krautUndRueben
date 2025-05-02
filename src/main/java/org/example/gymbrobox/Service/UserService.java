package org.example.gymbrobox.Service;

import org.example.gymbrobox.model.User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class UserService {




    public User createUser() {
        User user = new User();
        user.setUserName("Johnny");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@gmail.com");
        user.setPhoneNumber("040300300");
        user.setDateOfBirth(Date.valueOf(LocalDate.of(2025, 4, 29)));
        user.setAbo(false);
        user.setStreet("MyStreet");
        user.setHouseNumber("4");
        user.setZipCode("12345");
        user.setCityDistrict("hamburg");
        user.setCity("Hamburg");
        user.setCountry("Deutschland");

        return user;
    }


    public void printUser(User user) {

        System.out.println();
        System.out.println(user.getUserName());
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getEmail());
        System.out.println(user.getPhoneNumber());
        System.out.println(user.getDateOfBirth());
        System.out.println(user.isAbo());
        System.out.println(user.getStreet());
        System.out.println(user.getHouseNumber());
        System.out.println(user.getZipCode());
        System.out.println(user.getCityDistrict());
        System.out.println(user.getCity());
        System.out.println(user.getCountry());

    }



}
