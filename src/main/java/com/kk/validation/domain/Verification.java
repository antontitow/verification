package com.kk.validation.domain;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Titov 29.06.2021
 * Verification
 */
@Entity
@NoArgsConstructor
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
    private String active = "0";
    private String expire = "0";
    @NonNull
    private String dateCreate;

    /**
     * getId
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * getActive
     *
     * @return active
     */
    public String getActive() {
        return active;
    }

    /**
     * Verification ; constructor
     *
     * @param email
     * @param tokenCode
     */
    public Verification(String email, String tokenCode) {
        this.tokenCode = tokenCode;
        this.email = email;
        Date dateCreate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
        this.dateCreate = simpleDateFormat.format(dateCreate);
    }

    /**
     * isTokenExpired
     *
     * @return is token or code Expired
     * @throws ParseException
     */
    public boolean isTokenExpired() throws ParseException {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
        Date nowFormat = simpleDateFormat.parse(simpleDateFormat.format(now));

        Date dateCreateDate = simpleDateFormat.parse(this.dateCreate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateCreateDate);
        calendar.add(Calendar.DAY_OF_MONTH, lifeTime);
        Date dateDelay = calendar.getTime();
        if (nowFormat.after(dateDelay)) {
            return true;
        }
        return false;
    }

    /**
     * getTokenCode
     *
     * @return String
     */
    public String getTokenCode() {
        return tokenCode;
    }

    /**
     * setTokenCode
     *
     * @param tokenCode
     */
    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    /**
     * setActive
     */
    public void setActive() {
        this.active = "1";
    }

    /**
     * isExpire
     *
     * @return is token checked
     */
    public String getExpire() {
        return expire;
    }

    /**
     * setExpire ;marked token as expired
     */
    public void setExpire() {
        this.expire = "1";
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
