package com.waracle.cakemanagerserver.controller;

import com.waracle.cakemanagerserver.controller.exception.RestNoContentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Rest controller advice.
 */
@Slf4j
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RestNoContentException.class)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void noContentException() {
    }

}
