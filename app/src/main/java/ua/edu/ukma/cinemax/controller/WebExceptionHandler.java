package ua.edu.ukma.cinemax.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import ua.edu.ukma.cinemax.exception.InvalidIDException;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class WebExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public void handlerNotFound(NoHandlerFoundException ex) {
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class,
            EmptyResultDataAccessException.class,
            InvalidIDException.class})
    public void entityNotFound(Exception ex) {
    }
}
