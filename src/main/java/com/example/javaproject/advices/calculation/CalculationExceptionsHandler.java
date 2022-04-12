package com.example.javaproject.advices.calculation;

import com.example.javaproject.exceptions.calculation.WrongArgumentsOrderException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class CalculationExceptionsHandler {
    @ExceptionHandler(WrongArgumentsOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleWrongArgumentsOrderException(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleServerException(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
