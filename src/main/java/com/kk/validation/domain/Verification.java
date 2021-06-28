package com.kk.validation.domain;

import org.hibernate.annotations.Type;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Verification {
    private static final String formatDate = "yyyy.MM.dd";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String email;
    private String tokenCode;
    private String active ="0";
    private String revision= "0";
    @NonNull
    private String dateCreate;

    public Verification(){}
    public Verification(String email,String tokenCode){
        this.tokenCode = tokenCode;
        this.email = email;
        Date dateCreate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
        this.dateCreate = simpleDateFormat.format(dateCreate);
    }

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public void setActive() {
        this.active = "1";
    }

    public String isRevision() {
        return revision;
    }

    public void setRevision() {
        this.revision = "1";
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
