package com.kk.validation.repository;

import com.kk.validation.domain.Verification;
import org.springframework.data.repository.CrudRepository;

public interface VerificationRepo extends CrudRepository<Verification,Integer> {
    Verification findByEmail(String email);
}
