package com.kk.validation.service;

import com.kk.validation.domain.Verification;
import com.kk.validation.repository.VerificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Titov 29.06.2021
 * SQLiteSrv
 */
@Service
public class SQLiteSrv {
    @Autowired
    VerificationRepo verificationEntity;

    /**
     * saveMail
     *
     * @param mail
     * @param tokenOrCode
     * @return
     */
    public Verification saveMail(String mail, String tokenOrCode) {
        return verificationEntity.save(new Verification(mail, tokenOrCode));
    }
}
