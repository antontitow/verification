package com.kk.validation.domain;

import com.kk.validation.service.Token;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**
 * @author Titov 29.06.2021
 * GenerationRequest
 */
@NoArgsConstructor
@AllArgsConstructor
public class GenerationRequest {
    public GenerationRequest(String email) {
        this.email = email;
    }

    @Token(tokentype = {"token", "code"}, message = "not valid Token type")
    private String tokentype;

    @Email(regexp = "^([a-zA-Z0-9_\\.-]+)@([a-zA-Z0-9_\\.-]+)\\.([a-zA-Z]{2,6})$", message = "email not valid")
    private String email;

    private String status = "0";

    private String description = " ";

    private Integer id;

    /**
     * setId
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * getDescription
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getStatus
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * setStatus
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * getTokentype
     *
     * @return
     */
    public String getTokentype() {
        return tokentype;
    }

    /**
     * setTokentype
     *
     * @param tokentype
     */
    public void setTokentype(String tokentype) {
        this.tokentype = tokentype;
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

    @Override
    public String toString() {
        String str = "GenerationRequest{" +
                "\"email\":\"" + email + "\"," +
                "\"status\":\"" + status + "\"," +
                "\"description\":\"" + description + "\"";
        if (id != null) {
            str += ",\"id\":\"" + id + "\"";
        }
        str += "}";
        return str;
    }
}
