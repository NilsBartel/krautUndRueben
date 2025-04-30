package org.example.gymbrobox.api;

import org.example.gymbrobox.Service.LoginService;
import org.example.gymbrobox.Service.UserService;
import org.example.gymbrobox.model.UserAccount;
import org.example.gymbrobox.model.UserAccountWithSecurity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.Map;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }




    @PostMapping("/account/login")
    @ResponseBody
    public Map<String, String> login(@RequestBody UserAccount userAccount) {
        String token = loginService.login(userAccount);

        if (token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found, or password incorrect");
        }

        return Map.of("token", token);
    }

    @PostMapping("/account/register")
    @ResponseBody
    public String register(@RequestBody UserAccountWithSecurity userAccount) {


        return "success";
    }
}
