package com.bantuin.ticket.configuration;

import com.bantuin.ticket.util.ErrorCode;
import com.bantuin.ticket.util.LogUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.javan.keboot.core.exception.BadRequestException;
import id.co.javan.keboot.core.exception.ForbiddenException;
import id.co.javan.keboot.core.exception.UnauthorizedException;
import id.co.javan.keboot.core.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String TAG = "RestExceptionHandler";

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    protected ResponseEntity<Object> handleCustomException(Exception ex, WebRequest request) throws JsonProcessingException {

        ex.printStackTrace();
        ObjectMapper objectMapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // send response
        Map response = new HashMap();

        if (ex instanceof BadRequestException) {
            response.put("error_code", ((BadRequestException) ex).getCode());
            response.put("error_message", ex.getMessage());

            return handleExceptionInternal(ex, objectMapper.writeValueAsString(response),
                    headers, HttpStatus.BAD_REQUEST, request);
        }

        if (ex instanceof ValidationException) {
            Map errors = ((ValidationException) ex).getErrors();
            log.info("errors " + objectMapper.writeValueAsString(errors));

            response.put("error_code", ex.getMessage());
            response.put("data", errors);

            return handleExceptionInternal(ex, objectMapper.writeValueAsString(response),
                    headers, HttpStatus.BAD_REQUEST, request);
        }

        if (ex instanceof UnauthorizedException) {
            response.put("error_code", ErrorCode.ERROR.UNAUTHORIZED.name());
            response.put("error_message", ex.getMessage());
            return handleExceptionInternal(ex, objectMapper.writeValueAsString(response),
                    headers, HttpStatus.UNAUTHORIZED, request);
        }

        if (ex instanceof ForbiddenException) {
            response.put("error_code", ErrorCode.ERROR.FORBIDDEN.name());
            response.put("error_message", ex.getMessage());
            return handleExceptionInternal(ex, objectMapper.writeValueAsString(response),
                    headers, HttpStatus.FORBIDDEN, request);
        }

        if (ex instanceof NoSuchElementException) {
            response.put("error_code", ErrorCode.ERROR.DATA_NOT_FOUND.name());
            response.put("error_message", ErrorCode.ERROR.DATA_NOT_FOUND.name());
            return handleExceptionInternal(ex, objectMapper.writeValueAsString(response),
                    headers, HttpStatus.NOT_FOUND, request);
        }

        // here is unexpected exception part
        try {
            //Sentry.capture(ex);
        } catch (Exception e) {
            LogUtil.error(TAG, e);
            // nothing do to here
        }

        if (ex.getMessage() != null && ex.getMessage().contains("org.hibernate.exception.JDBCConnectionException")) {
            response.put("error_code", ErrorCode.ERROR.DATABASE_TIMEOUT.name());
            response.put("error_message", ErrorCode.ERROR.DATABASE_TIMEOUT.getMessage());
            return handleExceptionInternal(ex, objectMapper.writeValueAsString(response),
                    headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
        }

        response.put("error_message", ex.getMessage());
        response.put("error_code", ErrorCode.ERROR.INTERNAL_SERVER_ERROR.name());

        return handleExceptionInternal(ex, objectMapper.writeValueAsString(response),
                headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
