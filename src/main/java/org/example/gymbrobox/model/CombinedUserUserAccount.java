package org.example.gymbrobox.model;

public class CombinedUserUserAccount {
    private UserAccountWithSecurity userAccount;
    private User user;

    public CombinedUserUserAccount() {
    }

    public UserAccountWithSecurity getUserAccount() {
        return userAccount;
    }

    public User getUser() {
        return user;
    }

    public void setUserAccount(UserAccountWithSecurity userAccount) {
        this.userAccount = userAccount;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
