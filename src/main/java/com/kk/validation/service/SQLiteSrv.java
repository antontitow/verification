package com.kk.validation.service;

import com.kk.validation.domain.Verification;
import com.kk.validation.repository.VerificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SQLiteSrv {
    @Autowired
    VerificationRepo verificationEntity;
    public boolean saveMail(String mail){
        verificationEntity.save(new Verification(mail));
        return true;
    }
}
