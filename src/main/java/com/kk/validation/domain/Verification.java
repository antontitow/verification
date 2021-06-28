package com.kk.validation.domain;

import org.hibernate.annotations.Type;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Verification {
    // Формат хранимой даты
    private static final String formatDate = "dd.MM.yyyy";
    // Период жизни токена 7 дней
    private static final int lifeTime = 7;

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
    public boolean isTokenExpired() throws ParseException {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
        Date nowFormat = simpleDateFormat.parse(simpleDateFormat.format(now));

        Date dateCreateDate = simpleDateFormat.parse(this.dateCreate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateCreateDate);
        calendar.add(Calendar.DAY_OF_MONTH,lifeTime);
        Date dateDelay = calendar.getTime();
        if (nowFormat.after(dateDelay)){ return true;}
        return false;
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
