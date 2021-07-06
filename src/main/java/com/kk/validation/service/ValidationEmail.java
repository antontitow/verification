package com.kk.validation.service;

import com.kk.validation.domain.Verification;
import com.kk.validation.exceptions.ExceptionGenerationToken;
import com.kk.validation.exceptions.ExceptionTypeToken;
import com.kk.validation.repository.VerificationRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Titov 29.06.2021
 * ValidationEmail
 */
@Log4j2
@Service
public class ValidationEmail {

    private final TokenGen generation;
    private final SQLiteSrv sqLite;
    private final MailSenderSrv mailService;
    @Autowired
    VerificationRepo verificationEntity;

    /**
     * @param generation
     * @param sqLite
     * @param mailService
     */
    @Autowired
    public ValidationEmail(TokenGen generation, SQLiteSrv sqLite, MailSenderSrv mailService) {
        this.generation = generation;
        this.sqLite = sqLite;
        this.mailService = mailService;
    }

    private String email;
    private boolean emailValid;
    private boolean emailSend;
    private boolean isToken;
    private String tokenOrCode;
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
     * getId
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * setTokenOrCode
     *
     * @param tokenOrCode
     */
    public void setTokenOrCode(String tokenOrCode) {
        this.tokenOrCode = tokenOrCode;
    }

    /**
     * getTokenOrCode
     *
     * @return is Token or Code
     */
    public String getTokenOrCode() {
        return tokenOrCode;
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

    /**
     * Builder
     * configures the ValidationEmail bean
     */
    public class Builder {
        private final ValidationEmail validationEmail;

        /**
         * Builder
         */
        public Builder() {
            validationEmail = new ValidationEmail(generation, sqLite, mailService);
        }

        /**
         * @param email
         * @return Builder
         */
        public Builder setEmail(String email) {
            validationEmail.email = email;
            return this;
        }

        /**
         * @param tokenOrCode
         * @return Builder
         * @throws ExceptionTypeToken
         */
        public Builder isTokenOrCode(String tokenOrCode) throws ExceptionTypeToken {
            if (tokenOrCode.equals("token")) {
                validationEmail.isToken = true;
                return this;
            } else if (tokenOrCode.equals("code")) {
                validationEmail.isToken = false;
                return this;
            } else {
                throw new ExceptionTypeToken();
            }
        }

        /**
         * generateTokenOrCode
         *
         * @return Builder
         */
        public Builder generateTokenOrCode() {
            validationEmail.tokenOrCode = generation.token.get(validationEmail.isToken);
            return this;
        }

        /**
         * saveEmailAndTokenCode
         *
         * @return Builder
         */
        public Builder saveEmailAndTokenCode() {

            Verification verification = validationEmail.sqLite.saveMail(validationEmail.getEmail(), validationEmail.getTokenOrCode());
            validationEmail.id = verification.getId();
            return this;
        }

        /**
         * sendMail
         *
         * @return Builder
         */
        public Builder sendMail() {
            validationEmail.mailService.Send(validationEmail.getEmail(), validationEmail.getEmail(), validationEmail.getTokenOrCode(), "Активация имейла");
            return this;
        }

        /**
         * existTokeninDB
         *
         * @return Builder
         * @throws ExceptionGenerationToken
         */
        public Builder existTokeninDB() throws ExceptionGenerationToken {
            try {
                Verification verification = verificationEntity.findByTokenCode(validationEmail.getTokenOrCode());
            } catch (NullPointerException npex) {
                return this;
            }
            throw new ExceptionGenerationToken();
        }

        /**
         * Build
         *
         * @return ValidationEmail
         */
        public ValidationEmail Build() {
            return validationEmail;
        }
    }
}
