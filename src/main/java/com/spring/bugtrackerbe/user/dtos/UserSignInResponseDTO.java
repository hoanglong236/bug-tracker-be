package com.spring.bugtrackerbe.user.dtos;

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
