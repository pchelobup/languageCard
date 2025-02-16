package ru.alina.languageCards.exception.handler;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.alina.languageCards.exception.ControllerErrorMessage;
import ru.alina.languageCards.exception.NotFoundException;
import ru.alina.languageCards.exception.UnsuitableArgument;

import java.util.Date;


@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> resourceNotFoundException(NotFoundException ex, WebRequest request) {
        ControllerErrorMessage message = new ControllerErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false),
                new Date());

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> isNotValidRequest(MethodArgumentNotValidException ex, WebRequest request) {
        ControllerErrorMessage message = new ControllerErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false),
                new Date());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnsuitableArgument.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> unsuitableEntity(UnsuitableArgument ex, WebRequest request) {
        ControllerErrorMessage message = new ControllerErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false),
                new Date());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> unsuitableEntity(HttpMessageNotReadableException ex, WebRequest request) {
        ControllerErrorMessage message = new ControllerErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getDescription(false),
                new Date());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MalformedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> malformedJwtException(MalformedJwtException ex, WebRequest request) {
        ControllerErrorMessage message = new ControllerErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                "wrong jwt token",
                new Date());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        ex.printStackTrace();
        ControllerErrorMessage message = new ControllerErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                request.getDescription(false),
                new Date());

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
