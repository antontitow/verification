package com.kk.validation.service;

import com.kk.validation.exceptions.ExceptionTypeToken;
import com.kk.validation.exceptions.ExceptionNotValidEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class ValidationEmail {

    private final TokenGen generation;
    private final SQLiteSrv sqLite;
    private final MailSenderSrv mailService;

@Autowired
public ValidationEmail(TokenGen generation,SQLiteSrv sqLite,MailSenderSrv mailService){
    this.generation = generation;
    this.sqLite = sqLite;
    this.mailService = mailService;
}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;
    private boolean emailValid;
    private boolean emailSend;
    private boolean isToken;

    public void setTokenOrCode(String tokenOrCode) {
        this.tokenOrCode = tokenOrCode;
    }

    private String tokenOrCode;
    private boolean tokenOrCodeSave;

    public String getTokenOrCode() {
        return tokenOrCode;
    }

    public class Builder {
        private final ValidationEmail validationEmail;

        public Builder(){validationEmail = new ValidationEmail(generation,sqLite,mailService);}

        public Builder setEmail(String email){ validationEmail.email = email; return this;}

        public Builder validationEmail() throws ExceptionNotValidEmail {
            String regex = "^(.+)@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            if (pattern.matcher(validationEmail.email).matches())
            {validationEmail.emailValid = true; return this; }
                else {throw new ExceptionNotValidEmail();}
            }

        public Builder isTokenOrCode(String tokenOrCode) throws ExceptionTypeToken {
            if (tokenOrCode.equals("token"))
                {validationEmail.isToken = true; return this; }
            else if(tokenOrCode.equals("code")){validationEmail.isToken = false; return this; }
            else {throw new ExceptionTypeToken();}
        }

        public Builder generateTokenOrCode(){
            validationEmail.tokenOrCode = generation.token.get(validationEmail.isToken);
            return this;
        }
        public Builder saveEmailAndTokenCode(){
            validationEmail.sqLite.saveMail(validationEmail.getEmail(),validationEmail.getTokenOrCode());
            return this;
        }
        public Builder sendMail(){
            if (validationEmail.isToken) {validationEmail.mailService.Send( validationEmail.getEmail(),"Ghbdfdfd");}
            return this;
        }
        public ValidationEmail Build(){
            return validationEmail;
        }
    }
}
