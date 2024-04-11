package io.applova.testautomation.common.tokens;

import java.io.Serializable;

public class MerchantToken implements Serializable {
    private String username;
    private String password;
    private static MerchantToken merchantToken;

    public MerchantToken(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
