package com.ming.dictionary.common.advice;

import com.ming.dictionary.common.advice.annotation.UnknownExceptionHandler;
import com.ming.dictionary.common.exception.BaseException;
import com.ming.dictionary.common.exception.ResponseInfoEnum;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author ming
 */
@Aspect
@Component
public class UnknownExceptionAdvice {


    @Pointcut("within(com.ming.dictionary..*)")
    public void withinPackage() {}


    /**
     * 通过注解{@link UnknownExceptionHandler}支持的未知异常处理
     * 如果项目基础异常{@link BaseException}的子类，直接抛出
     * 如果是未知异常，则需要获取{@link UnknownExceptionHandler}注解的异常信息
     * @param ex 方法本来抛出的异常
     * @param unknownExceptionHandler 目标方法的注解
     * @throws Throwable 抛出原本异常或者包装异常
     */
    @AfterThrowing(pointcut = "@annotation(unknownExceptionHandler)" +
            "&& withinPackage() "
            , throwing = "ex"
            , argNames = "ex,unknownExceptionHandler"
    )
    public void unknownExceptionHandler(Throwable ex, UnknownExceptionHandler unknownExceptionHandler) throws Throwable {
        if (ex instanceof BaseException) {
            throw ex;
        }else{
            BaseException baseException;
            try {
                String msg = unknownExceptionHandler.msg();
                if(StringUtils.isNotEmpty(msg)) {
                    baseException = unknownExceptionHandler.baseException().getConstructor(String.class,Throwable.class).newInstance(msg,ex);
                }else{
                    baseException = unknownExceptionHandler.baseException().getConstructor(Throwable.class).newInstance(ex);
                }
            } catch (Exception e) {
                throw new BaseException() {
                    @Override
                    protected ResponseInfoEnum getInfoEnum() {
                        return ResponseInfoEnum.UNKNOWN_EXCEPTION;
                    }
                };
            }
            throw baseException;
        }
    }
}
