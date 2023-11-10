package com.spring.bugtrackerbe.dto;

public class SignInResponseDTO {

    private String authorizationToken;

    public SignInResponseDTO(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }
}
