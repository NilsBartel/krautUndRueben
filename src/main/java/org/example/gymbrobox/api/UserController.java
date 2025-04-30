package org.example.gymbrobox.api;

import org.example.gymbrobox.Service.UserService;
import org.example.gymbrobox.database.UserRepo;
import org.example.gymbrobox.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;
    private final UserRepo userRepo;
    public UserController(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @GetMapping("/user")
    @ResponseBody
    public User getUserByName(@RequestParam String username) {
        return userRepo.findByUsername(username);
    }




}
