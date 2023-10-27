package com.spring.bugtrackerbe.user.dto;

public class UserSignInResponseDTO {

    private String authorizationToken;

    public UserSignInResponseDTO(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }
}
