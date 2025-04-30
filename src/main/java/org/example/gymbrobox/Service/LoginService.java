package org.example.gymbrobox.Service;

import org.example.gymbrobox.database.LoginRepo;
import org.example.gymbrobox.model.UserAccount;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginService {

    private final HashService hashService;
    private final LoginRepo loginRepo;
    private final AuthenticationTokenService tokenService;

    public LoginService(HashService hashService, LoginRepo loginRepo, AuthenticationTokenService tokenService) {
        this.hashService = hashService;
        this.loginRepo = loginRepo;
        this.tokenService = tokenService;
    }

    public String login(UserAccount userAccount) {
        String passHash = loginRepo.getPasswordByUsername(userAccount);

        if (hashService.verify(userAccount.getPassword(), passHash)) {
            return tokenService.create(userAccount.getUsername());
        }
        return "";
    }

}
