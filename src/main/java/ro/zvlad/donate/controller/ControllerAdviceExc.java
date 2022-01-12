package ro.zvlad.donate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.zvlad.donate.dto.ErrorResponse;
import ro.zvlad.donate.exceptions.GeneralException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerAdviceExc {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ErrorResponse> handleCauseException(GeneralException ex) {
        return ResponseEntity.status(ex.getCode()).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        var firstError=ex.getAllErrors().get(0);
        String field=firstError instanceof FieldError fe ? fe.getField() : "";
        String message="Invalid value on field "+field;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintException(ConstraintViolationException ex) {
        String message="Invalid value on field "+ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleConstraintException(IllegalArgumentException ex) {
        String message=ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message));
    }

}
