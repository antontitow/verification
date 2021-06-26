package com.kk.validation.domain;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Verification {
    private static final String formatDate = "yyyy.MM.dd";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public String getTokenOrCode() {
        return tokenOrCode;
    }

    public void setTokenOrCode(String tokenOrCode) {
        this.tokenOrCode = tokenOrCode;
    }

    private String tokenOrCode;
    private boolean active;

    private String email;
    @NonNull
    private String dateCreate;


    public Verification(){}
    public Verification(String email,String tokenOrCode){
        this.tokenOrCode = tokenOrCode;
        this.email = email;
        Date dateCreate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
        this.dateCreate = simpleDateFormat.format(dateCreate);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
