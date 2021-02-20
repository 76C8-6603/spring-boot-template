package com.ming.dictionary.filter;

import com.ming.dictionary.common.exception.BaseException;
import com.ming.dictionary.common.exception.ResponseInfoEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * post body大小限制
 * @author 78c8-6603
 */
@Order(2)
@Component
@Slf4j
public class PostBodyFilter implements Filter {

    @Value("${filter.post.max-content-length:#{null}}")
    private Long maxContentLength;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(!verify()){
            chain.doFilter(request, response);
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (httpServletRequest.getContentLengthLong() > maxContentLength) {
            throw new BaseException(ResponseInfoEnum.POST_CONTENT_OVERLOAD.getMessage()) {
                @Override
                protected ResponseInfoEnum getInfoEnum() {
                    return ResponseInfoEnum.POST_CONTENT_OVERLOAD;
                }
            };
        }
        chain.doFilter(request,response);
    }

    private boolean verify() {
        return maxContentLength != null && maxContentLength > 0;
    }
}
