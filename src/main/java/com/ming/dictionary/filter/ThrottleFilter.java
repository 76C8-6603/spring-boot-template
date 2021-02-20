package com.ming.dictionary.filter;

import com.ming.dictionary.common.exception.BaseException;
import com.ming.dictionary.common.exception.ResponseInfoEnum;
import io.github.bucket4j.Bucket;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 限流
 * @author 76c8-6603
 */
@Order(1)
@Component
public class ThrottleFilter implements Filter {
     @Nullable
     private Bucket bucket;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (bucket == null) {
            chain.doFilter(request, response);
        }

        if (bucket.tryConsume(1)) {
            chain.doFilter(request,response);
        }else{
            throw new BaseException(ResponseInfoEnum.TOO_MANY_REQUEST.getMessage()) {
                @Override
                protected ResponseInfoEnum getInfoEnum() {
                    return ResponseInfoEnum.TOO_MANY_REQUEST;
                }
            };
        }
    }

    @Autowired(required = false)
    public void setBucket(@Nullable Bucket bucket) {
        this.bucket = bucket;
    }
}
