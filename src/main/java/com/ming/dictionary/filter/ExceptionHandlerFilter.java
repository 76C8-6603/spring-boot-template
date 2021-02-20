package com.ming.dictionary.filter;

import cn.hutool.http.ContentType;
import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ming.dictionary.common.exception.BaseException;
import com.ming.dictionary.common.exception.ResponseInfoEnum;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * filter异常公共处理
 * @author 78c8-6603
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@Component
public class ExceptionHandlerFilter implements Filter {

    private final ObjectMapper objectMapper;

    public ExceptionHandlerFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }



    private void writeException(Exception e, HttpServletResponse httpServletResponse, String writeValueAsString) throws IOException {
        log.error(e.getMessage(), e);
        httpServletResponse.setStatus(HttpStatus.HTTP_OK);
        httpServletResponse.setContentType(ContentType.JSON.toString());
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.getWriter().append(writeValueAsString);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try {
            chain.doFilter(request, response);
        } catch (BaseException e) {
            writeException(e,httpServletResponse,objectMapper.writeValueAsString(e.getResultEntity()));
        } catch (Exception e) {
            writeException(e,httpServletResponse,objectMapper.writeValueAsString(ResponseInfoEnum.UNKNOWN_EXCEPTION.buildResultEntity()));
        }
    }
}
