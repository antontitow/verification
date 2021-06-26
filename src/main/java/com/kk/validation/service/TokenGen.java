package com.kk.validation.service;

import com.kk.validation.config.TokenGenConfig;
import com.kk.validation.domain.GeneratedToken;
import com.kk.validation.exceptions.ExceptionTypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

@Service
public class TokenGen {
    @Autowired
    private TokenGenConfig tokenGenConfig;

    public GeneratedToken token = (t)->{
        if (t){
            SecureRandom secureRandom = new SecureRandom(); //threadsafe
            Base64.Encoder base64Encoder = Base64.getUrlEncoder();
            byte[] randomBytes = new byte[tokenGenConfig.getLengthToken()];
            secureRandom.nextBytes(randomBytes);
            return base64Encoder.encodeToString(randomBytes);}
        else {
            StringBuilder STR = new StringBuilder();
            int[] iArr = new Random().ints(tokenGenConfig.getLengthCode(),0,9).toArray();
            Arrays.stream(iArr).forEach((i)->STR.append(i));
            return STR.toString();
        }
    };
}
