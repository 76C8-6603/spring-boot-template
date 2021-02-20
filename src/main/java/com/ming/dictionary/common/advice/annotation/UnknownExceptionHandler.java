package com.ming.dictionary.common.advice.annotation;

import com.ming.dictionary.common.exception.BaseException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ming
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UnknownExceptionHandler {
    String msg() default "";
    Class<? extends BaseException> baseException();
}
