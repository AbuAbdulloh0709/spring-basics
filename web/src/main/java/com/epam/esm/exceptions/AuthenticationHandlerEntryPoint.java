package com.epam.esm.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.epam.esm.exceptions.ExceptionCodes.UNAUTHORIZED_EXCEPTION;

@Component
public class AuthenticationHandlerEntryPoint implements AuthenticationEntryPoint {
    private static final String ENCODING = "UTF-8";
    private static final String UNAUTHORIZED_MESSAGE = "exception.unauthorized";
    private final MessageSource messageSource;

    @Autowired
    public AuthenticationHandlerEntryPoint(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authException) throws IOException {
        httpServletResponse.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(ENCODING);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        List<String> details = Collections.singletonList(messageSource.getMessage(UNAUTHORIZED_MESSAGE, new String[]{}, httpServletRequest.getLocale()));
        httpServletResponse.getWriter()
                .write(String.valueOf(new ObjectMapper()
                        .writeValueAsString(new ErrorResponse(UNAUTHORIZED_EXCEPTION.toString(), details))));
    }
}
