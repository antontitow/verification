package com.kk.validation.domain;

import com.kk.validation.exceptions.ExceptionTypeToken;

/**
 * @author Titov 29.06.2021
 * GeneratedToken
 */
@FunctionalInterface
public interface GeneratedToken {
    /**
     * get
     *
     * @param type
     * @return token
     */
    String get(boolean type);
}
