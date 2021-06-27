package com.kk.validation.repository;

import com.kk.validation.domain.Verification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepo extends CrudRepository<Verification,Integer> {
    @Query("select * from validation where email = ?1 and active = 0 and validation = 0")
    Verification findByEmail(String email);
    Verification findByTokenCode(String token);

}
