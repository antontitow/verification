package com.kk.validation.repository;

import com.kk.validation.domain.Verification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Titov 29.06.2021
 * VerificationRepo
 */
@Repository
public interface VerificationRepo extends CrudRepository<Verification, Integer> {
    /**
     * findById
     * @param id
     * @return
     */
    Optional<Verification> findById(Integer id);
    /**
     * findByEmail
     *
     * @param email
     * @return Verification
     */
    @Query(value = "select * from verification where email = ?1 and expire = 0", nativeQuery = true)
    Verification findByEmail(String email);

    /**
     * findByTokenCode
     *
     * @param token
     * @return Verification
     */
    Verification findByTokenCode(String token);

    /**
     * findByEmailAndTokenCode
     *
     * @param email
     * @param token
     * @return Verification
     */
    @Query(value = "select * from verification where email = ?1 and token_code = ?2", nativeQuery = true)
    Verification findByEmailAndTokenCode(String email, String token);
    //void setUserParameters();

    @Query(value = "select * from verification where expire = 0", nativeQuery = true)
    List<Verification> findByExpire();
}
