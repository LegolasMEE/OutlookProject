package com.Trainee.ProjectOutlook.model;

public class JwtResponse {

    private String token;
    private String type = "Bearer"; // Тип токена (обычно "Bearer")

    public JwtResponse(String token) {
        this.token = token;
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
}
