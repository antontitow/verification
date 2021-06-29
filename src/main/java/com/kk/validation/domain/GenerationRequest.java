package com.kk.validation.domain;

/**
 * @author Titov 29.06.2021
 * GenerationRequest
 */
public class GenerationRequest {
    protected GenerationRequest() {
    }

    private String tokenType;
    private String email;

    /**
     * getTokenType
     *
     * @return type of token
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * setTokenType
     *
     * @param tokenType set type of token
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * getEmail
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * setEmail
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
