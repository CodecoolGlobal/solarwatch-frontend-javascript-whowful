package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.exception.NotSupportedCityName;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SolarWatchControllerAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidDateExceptionHandler(InvalidDateException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NotSupportedCityName.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidCityNameException(NotSupportedCityName ex){
        return ex.getMessage();
    }
}
