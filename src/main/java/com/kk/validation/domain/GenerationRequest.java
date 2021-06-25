package com.kk.validation.domain;

public class GenerationRequest {
    protected GenerationRequest(){}
    private String tokenType;
    private String email;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
