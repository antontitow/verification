package com.kk.validation.service;

import com.kk.validation.exceptions.ExceptionTypeToken;
import com.kk.validation.exceptions.ExceptionNotValidEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class ValidationEmail {
//    @Autowired
//    private final MailSenderSrv mailService;
//    private final SQLiteSrv sqLite;


private final TokenGen generation;
@Autowired
public ValidationEmail(TokenGen generation){
    this.generation = generation;
}



//    @Autowired
//    public ValidationEmail(TokenGen generation, SQLiteSrv sqLite, MailSenderSrv mailService) {
//        this.generation = generation;
//        this.sqLite = sqLite;
//        this.mailService = mailService;
//    }



    private String email;
    private boolean emailValid;
    private boolean emailSend;
    private boolean isToken;
    private String tokenOrCode;
    private boolean tokenOrCodeSave;

    public String getTokenOrCode() {
        return tokenOrCode;
    }


    public class Builder {
        private ValidationEmail validationEmail;

        public Builder(){validationEmail = new ValidationEmail(generation);}

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
        public ValidationEmail Build(){
            return validationEmail;
        }
    }
}
