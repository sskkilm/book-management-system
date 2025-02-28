package com.sskkilm.bookmanagementsystem.common.presentation;

import com.sskkilm.bookmanagementsystem.common.error.ErrorCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorResponse {

    ErrorCode[] value();
}
