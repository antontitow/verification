package com.kk.validation.domain;

import com.kk.validation.exceptions.ExceptionTypeToken;

@FunctionalInterface
public interface GeneratedToken {
    String get(boolean type);
}
