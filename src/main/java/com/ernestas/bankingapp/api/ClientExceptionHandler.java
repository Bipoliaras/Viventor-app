package com.ernestas.bankingapp.api;

import com.ernestas.bankingapp.exception.BadRequestException;
import com.ernestas.bankingapp.exception.ExceptionResponse;
import com.ernestas.bankingapp.exception.InternalServerException;
import com.ernestas.bankingapp.exception.NotFoundException;
import com.ernestas.bankingapp.exception.WebException;
import java.time.ZonedDateTime;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice("com.ernestas.bankingapp.api")
@Component
public class ClientExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value={NotFoundException.class})
  @ResponseStatus(value=HttpStatus.NOT_FOUND)
  public @ResponseBody
  ExceptionResponse handleNotFoundException(Exception e) {
    return createResponse(e);
  }

  @ExceptionHandler(value={ConstraintViolationException.class})
  @ResponseStatus(value=HttpStatus.BAD_REQUEST)
  public @ResponseBody
  ExceptionResponse handleConstraintException(ConstraintViolationException e) {
    return createResponse(new BadRequestException(
        e.getConstraintViolations().toString()));
  }

  @ExceptionHandler(value={BadRequestException.class})
  @ResponseStatus(value=HttpStatus.BAD_REQUEST)
  public @ResponseBody
  ExceptionResponse handleBadRequestException(Exception e) {
    return createResponse(e);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
  public @ResponseBody
  ExceptionResponse handleInternalServerException(Exception e) {
    return createResponse(new InternalServerException(e.getMessage()));
  }

  private ExceptionResponse createResponse(Exception ex) {

    String message = "There was a problem processing your request";
    String debug;
    if (ex instanceof WebException) {
      debug = ex.getMessage();
    } else if (ex instanceof ConstraintViolationException) {
      ConstraintViolationException cve = (ConstraintViolationException) ex;
      debug = cve.getConstraintViolations().toString();
    } else {
      debug = (ex.getMessage());
    }

    return ExceptionResponse.builder()
        .message(message)
        .debug(debug)
        .timestamp(ZonedDateTime.now())
        .build();
  }
}
