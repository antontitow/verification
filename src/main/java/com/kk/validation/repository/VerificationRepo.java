package com.kk.validation.repository;

import com.kk.validation.domain.Verification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepo extends CrudRepository<Verification,Integer> {
    @Query(value="select * from verification where email = ?1 and active = 0 and revision = 0",nativeQuery = true)
    Verification findByEmail(String email);
    Verification findByTokenCode(String token);
    @Query(value="select * from verification where email = ?1 and token_code = ?2 and active = 0 and revision = 0",nativeQuery = true)
    Verification findByEmailAndTokenCode(String email,String token);
    //void setUserParameters();

}
