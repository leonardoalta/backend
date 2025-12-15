package com.blacksystem.system.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private String email;
    private String nameUser;
    public AuthResponse() {
    }

    public AuthResponse(String token, String email, String nameUser) {
        this.token = token;
        this.email = email;
        this.nameUser = nameUser;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
}
