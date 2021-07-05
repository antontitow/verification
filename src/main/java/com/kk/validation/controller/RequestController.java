package com.kk.validation.controller;

import com.kk.validation.domain.GenerationRequest;
import com.kk.validation.domain.Verification;
import com.kk.validation.exceptions.ExceptionTypeToken;
import com.kk.validation.repository.VerificationRepo;
import com.kk.validation.service.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.text.ParseException;
import java.util.Optional;

/**
 * @author Titov 29.06.2021
 * RequestController
 */
@RestController
public class RequestController {
    private final TokenGen generation;
    private final SQLiteSrv sqLite;
    private final MailSenderSrv mailService;
    private final VerificationRepo verificationEntity;

    /**
     * RequestController
     *
     * @param generation
     * @param sqLite
     * @param mailService
     * @param verificationEntity
     */
    @Autowired
    public RequestController(TokenGen generation, SQLiteSrv sqLite, MailSenderSrv mailService, VerificationRepo verificationEntity) {
        this.generation = generation;
        this.sqLite = sqLite;
        this.mailService = mailService;
        this.verificationEntity = verificationEntity;
    }

    /**
     * check
     *
     * @return responseEntity
     */
    @SneakyThrows
    @GetMapping("/check/{id}")
    public ResponseEntity<String> check(@PathVariable Integer id) throws ParseException {
        Optional<Verification> optionalVerification = verificationEntity.findById(id);
        GenerationRequest generationRequest = new GenerationRequest();
        if (optionalVerification.equals(null)) {
            generationRequest.setStatus("4");
            generationRequest.setDescription("ID not found.");
        } else {
            Verification verification = optionalVerification.get();
            generationRequest.setEmail(verification.getEmail());
            if (verification.getExpire().equals("0")) {
                if (verification.isTokenExpired() == true) {
                    verification.setExpire();
                    verificationEntity.save(verification);
                    try {
                        verification = verificationEntity.findByEmail(verification.getEmail());
                        if (verification.getActive().equals("1")) {
                            generationRequest.setStatus("5");
                            generationRequest.setDescription("New ID. Email confirm.");
                            generationRequest.setId(verification.getId());
                        } else {
                            generationRequest.setStatus("6");
                            generationRequest.setDescription("New ID. Email confirmation pending");
                            generationRequest.setId(verification.getId());
                        }
                    } catch (NullPointerException npex) {
                        generationRequest.setStatus("4");
                        generationRequest.setDescription("ID not found.");
                    }
                } else {
                    generationRequest.setId(id);
                    if (verification.getActive().equals("1")) {
                        generationRequest.setStatus("1");
                        generationRequest.setDescription("Email confirm.");
                    } else {
                        generationRequest.setStatus("2");
                        generationRequest.setDescription("Email confirmation pending.");
                    }
                }
            } else {
                try {
                    verification = verificationEntity.findByEmail(verification.getEmail());
                    if (verification.getActive().equals("1")) {
                        generationRequest.setStatus("5");
                        generationRequest.setDescription("New ID. Email confirm.");
                        generationRequest.setId(verification.getId());
                    } else {
                        generationRequest.setStatus("6");
                        generationRequest.setDescription("New ID. Email confirmation pending");
                        generationRequest.setId(verification.getId());
                    }
                } catch (NullPointerException npex) {
                    generationRequest.setStatus("4");
                    generationRequest.setDescription("ID not found.");
                }
            }
        }
        return new ResponseEntity(generationRequest.toString(), HttpStatus.OK);
//        try {
//            Verification verification = verificationEntity.findByEmail(email);
//            if (verification.equals(null)) {
//                return new ResponseEntity("Token absent", HttpStatus.BAD_REQUEST);
//            } else {
//                if (verification.isTokenExpired()) {
//                    verification.setExpire();
//                    verificationEntity.save(verification);
//                    return new ResponseEntity("Token expired", HttpStatus.BAD_REQUEST);
//                }
//                if (verification.getActive().equals("1")) {
//                    return new ResponseEntity("Token verified", HttpStatus.OK);
//
//                } else {
//                    return new ResponseEntity("Token hasn't been verified", HttpStatus.OK);
//                }
//            }
//        } catch (NullPointerException ex) {
//            return new ResponseEntity("email not found", HttpStatus.BAD_REQUEST);
//        }
    }

    /**
     * generationToken2
     *
     * @param genRequest
     * @return responseEntity
     */

    //через JSON
    @SneakyThrows
    @PostMapping(path = "/generate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> generationToken2(@RequestBody @Valid GenerationRequest genRequest) {
        Verification verification = verificationEntity.findByEmail(genRequest.getEmail());
        GenerationRequest generationRequest = new GenerationRequest(genRequest.getEmail());
        if (verification != null) {
            if (verification.isTokenExpired()) {
                verification.setExpire();
                verificationEntity.save(verification);
                ValidationEmail vemail = new ValidationEmail(generation, sqLite, mailService).new Builder()
                        .setEmail(genRequest.getEmail())
                        .isTokenOrCode(genRequest.getTokentype())
                        .generateTokenOrCode()
                        .existTokeninDB()
                        .saveEmailAndTokenCode()
                        .sendMail()
                        .Build();
                //вернуть ID нового токена
                generationRequest.setStatus("3");
                generationRequest.setId(vemail.getId());
                generationRequest.setDescription("A new token was generated. The previous token is deprecated");
            } else {
                if (verification.getActive().equals("1")) {
                    generationRequest.setStatus("1");
                    generationRequest.setId(verification.getId());
                    generationRequest.setDescription("Email request already exists. Email confirm");
                } else {
                    generationRequest.setStatus("2");
                    generationRequest.setId(verification.getId());
                    generationRequest.setDescription("Email request already exists. Email confirmation pending.");
                }
            }
        } else {
            ValidationEmail vemail = new ValidationEmail(generation, sqLite, mailService).new Builder()
                    .setEmail(genRequest.getEmail())
                    .isTokenOrCode(genRequest.getTokentype())
                    .generateTokenOrCode()
                    .existTokeninDB()
                    .saveEmailAndTokenCode()
                    .sendMail()
                    .Build();
            //вернуть ID
            generationRequest.setStatus("3");
            generationRequest.setId(vemail.getId());
            generationRequest.setDescription("A new token was generated.");
        }
        return new ResponseEntity(generationRequest.toString(), HttpStatus.OK);
    }

    /**
     * @param email
     * @param token
     * @return responseEntity
     * @throws ParseException activation
     */
    @GetMapping("/activation/{email}/{token}")
    public ResponseEntity<String> activation(@PathVariable @Email(regexp = "^([a-zA-Z0-9_\\.-]+)@([a-zA-Z0-9_\\.-]+)\\.([a-zA-Z]{2,6})$",
            message = "email not valid") String email,
                                             @PathVariable @Token(tokentype = {"token", "code"}, message = "not valid Token type") String token) throws ParseException {
        try {
            Verification row = verificationEntity.findByEmailAndTokenCode(email, token);
            // if token expired, return error
            if (row.getExpire().equals("1")) {
                return new ResponseEntity("token expired", HttpStatus.BAD_REQUEST);
                //otherwise, make a check whether the token is expired
            } else {
                if (row.isTokenExpired()) {
                    row.setExpire();
                    verificationEntity.save(row);
                    return new ResponseEntity("Token expired", HttpStatus.BAD_REQUEST);
                }
            }
            row.setActive();
            verificationEntity.save(row);
            return new ResponseEntity("Почтовый ящик проверен", HttpStatus.OK);
            //there are no emails with such a token
        } catch (NullPointerException npex) {
            // there are no such tokens
            return new ResponseEntity("Error authentification", HttpStatus.BAD_REQUEST);
        }
    }
}