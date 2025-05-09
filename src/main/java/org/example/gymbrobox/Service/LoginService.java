package org.example.gymbrobox.Service;

import org.example.gymbrobox.database.LoginRepo;
import org.example.gymbrobox.model.User;
import org.example.gymbrobox.model.UserAccount;
import org.example.gymbrobox.model.UserAccountWithSecurity;
import org.springframework.stereotype.Service;

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
        System.out.println(passHash);
        System.out.println(userAccount.getPassword());

        if (hashService.verify(userAccount.getPassword(), passHash)) {
            return tokenService.create(userAccount.getUsername());
        }
        return "";
    }

    public boolean register(UserAccountWithSecurity userAccount, User user) {

        UserAccountWithSecurity userAccountWithHashedPass = new UserAccountWithSecurity();
        userAccountWithHashedPass.setUsername(userAccount.getUsername());
        userAccountWithHashedPass.setPassword(hashService.hash(userAccount.getPassword()));
        userAccountWithHashedPass.setSecurityAnswer(hashService.hash(userAccount.getSecurityAnswer()));
        return loginRepo.createUser(userAccountWithHashedPass, user);
    }

    public boolean resetPassword(UserAccountWithSecurity userAccount) {
        String hashedAnswer = loginRepo.getSecurityAnswerByUsername(userAccount);

        if (hashService.verify(userAccount.getSecurityAnswer(), hashedAnswer)) {
            userAccount.setPassword(hashService.hash(userAccount.getPassword()));
            loginRepo.updatePasswordByUsername(userAccount);
            return true;
        } else {
            return false;
        }
    }
}
