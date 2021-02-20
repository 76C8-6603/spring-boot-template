package com.ming.dictionary.common.advice;

import com.ming.dictionary.common.exception.BaseException;
import com.ming.dictionary.common.exception.ResponseInfoEnum;
import com.ming.dictionary.common.exception.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;

/**
 * @author ming
 */
@RestControllerAdvice
@Slf4j
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 要包装返回值的目标方法，目前设定是被@RequestMapping注解修饰的方法(也就是所有controller类的方法)。
     * 如果只想局部调整，不想影响全局controller，可以自定义一个注解
     */
    private static final Class<? extends Annotation> ANNOTATION_TYPE = RequestMapping.class;

    @Override
    public boolean supports( MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        if (methodParameter.getMethod() != null) {
            return AnnotatedElementUtils.hasMetaAnnotationTypes(methodParameter.getMethod(),ANNOTATION_TYPE);

        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof ResultEntity) {
            return o;
        }
        return ResultEntity.builder().code(ResponseInfoEnum.SUCCESS.getCode()).msg(ResponseInfoEnum.SUCCESS.getMessage()).data(o).build();
    }

    @ExceptionHandler(value = BaseException.class)
    public ResultEntity baseExceptionHandler(BaseException baseException) {
        log.error(baseException.getMessage(), baseException);
        return baseException.getResultEntity();
    }

    @ExceptionHandler(value = Exception.class)
    public ResultEntity unknownExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new BaseException() {
            @Override
            protected ResponseInfoEnum getInfoEnum() {
                return ResponseInfoEnum.UNKNOWN_EXCEPTION;
            }
        }.getResultEntity();
    }
}
