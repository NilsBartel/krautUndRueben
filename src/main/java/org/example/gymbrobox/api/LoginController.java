package org.example.gymbrobox.api;

import org.example.gymbrobox.Service.AuthenticationTokenService;
import org.example.gymbrobox.Service.LoginService;
import org.example.gymbrobox.model.CombinedUserUserAccount;
import org.example.gymbrobox.model.User;
import org.example.gymbrobox.model.UserAccount;
import org.example.gymbrobox.model.UserAccountWithSecurity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class LoginController {

    private final LoginService loginService;
    private final AuthenticationTokenService authenticationTokenService;

    public LoginController(LoginService loginService, AuthenticationTokenService authenticationTokenService) {
        this.loginService = loginService;
        this.authenticationTokenService = authenticationTokenService;
    }




    @PostMapping("/account/login")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public Map<String, String> login(@RequestBody UserAccount userAccount) {
        String token = loginService.login(userAccount);

        if (token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login failed");
        }
        return Map.of("token", token);
    }

    @RequestMapping(value = "/account/registers", method = RequestMethod.OPTIONS)
    public String options() {
        System.out.println("OPTIONS");
        return "OPTIONS";
    }




    @PostMapping("/account/register")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public Map<String, String> register(@RequestBody CombinedUserUserAccount combinedUserUserAccount) {
        System.out.println("REGISTER");
        User user = combinedUserUserAccount.getUser();
        UserAccountWithSecurity userAccount = combinedUserUserAccount.getUserAccount();

        if (!loginService.register(userAccount, user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "DatabaseError");
        }
        UserAccount newUserAccount = new UserAccount();
        newUserAccount.setUsername(userAccount.getUsername());
        newUserAccount.setPassword(userAccount.getPassword());

        String token = loginService.login(newUserAccount);
        if (token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login failed");
        }

        return Map.of("token", token);
    }

    @PostMapping("/account/resetPassword")
    @CrossOrigin(origins = "http://localhost:3000")
    public Map<String, String> resetPassword(@RequestBody UserAccountWithSecurity userAccount) {
        if (!loginService.resetPassword(userAccount)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Security Answer");
        }

        return Map.of("status", "success");
    }

    @PostMapping("/logout")
    @CrossOrigin(origins = "http://localhost:3000")
    public Map<String, String> logout(@RequestHeader("token") String token) {
        if (!authenticationTokenService.logout(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        return Map.of("status", "success");
    }
}
