package ru.sorago.homeinvandroid.data.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;

import java.sql.Timestamp;
import java.time.Instant;

import ru.sorago.homeinvandroid.data.model.User;
import ru.sorago.homeinvandroid.data.response.base.Response;
import ru.sorago.homeinvandroid.data.response.type.LoggingUser;

public class LoginResponse extends Response {
    private User loggingUser;
    private String token;

    public LoginResponse() {
        super();
    }

    public User getLoggingUser() {
        return loggingUser;
    }

    public void setLoggingUser(User loggingUser) {
        this.loggingUser = loggingUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
