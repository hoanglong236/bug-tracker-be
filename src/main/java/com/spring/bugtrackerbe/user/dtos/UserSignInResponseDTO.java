package com.spring.bugtrackerbe.user.dtos;

public class UserSignInResponseDTO {

    private String authorizeToken;

    public UserSignInResponseDTO(String authorizeToken) {
        this.authorizeToken = authorizeToken;
    }

    public String getAuthorizeToken() {
        return authorizeToken;
    }

    public void setAuthorizeToken(String authorizeToken) {
        this.authorizeToken = authorizeToken;
    }
}
