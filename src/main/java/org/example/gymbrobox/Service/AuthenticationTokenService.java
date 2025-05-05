package org.example.gymbrobox.Service;

import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public final class AuthenticationTokenService {

    //private final int TOKEN_LENGTH = 32;
    private final int TOKEN_LIFETIME_MINUTES = 4320; // 3 days
    private final Map<String, Map.Entry<String, Timestamp>> authMap = new HashMap<>();


    public String create(String username) {
        String token = UUID.randomUUID().toString();
        //String token = RandomString.make(TOKEN_LENGTH);
        authMap.put(
            token,
            Map.entry(username, new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(TOKEN_LIFETIME_MINUTES)))
        );
        return token;
    }


    public boolean authenticate(String authToken) {
        return authMap.containsKey(authToken) && timestampValid(authMap.get(authToken).getValue());
    }

    public boolean logout(String authToken) {

        if (!authMap.containsKey(authToken) || !timestampValid(authMap.get(authToken).getValue())) {
            return false;
        }
        authMap.remove(authToken);
        return true;
    }

    private boolean timestampValid(Timestamp timestamp) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return now.before(timestamp);
    }


    public String getUsername(String authToken){
        return authMap.get(authToken).getKey();
    }
}
