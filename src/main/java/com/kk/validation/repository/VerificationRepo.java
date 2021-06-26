package com.kk.validation.repository;

import com.kk.validation.domain.Verification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepo extends CrudRepository<Verification,Integer> {
    Verification findByEmail(String email);
    Verification findByTokenCode(String token);

}
