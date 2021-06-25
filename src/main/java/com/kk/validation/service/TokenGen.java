package com.kk.validation.service;

import com.kk.validation.domain.GeneratedToken;
import com.kk.validation.exceptions.ExceptionTypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TokenGen {
    @Value("${tokengen.lengthToken}")
    private int lengthToken ;
    @Value("${tokengen.lengthCode}")
    private int lengthCode ;
    public GeneratedToken token = (t)->{
        if (t.equals("token")){
            SecureRandom secureRandom = new SecureRandom(); //threadsafe
            Base64.Encoder base64Encoder = Base64.getUrlEncoder();
            byte[] randomBytes = new byte[lengthToken];
            secureRandom.nextBytes(randomBytes);
            return base64Encoder.encodeToString(randomBytes);}
        else if (t.equals("code")){
            StringBuilder STR = new StringBuilder();
            int[] iArr = new Random().ints(lengthCode,0,9).toArray();
            Arrays.stream(iArr).forEach((i)->STR.append(i));
            return STR.toString();
        }else {
            throw new ExceptionTypeToken();
        }
    };
}
