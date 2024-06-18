package com.codecool.solarwatch.exception;

public class NotSupportedCityName extends RuntimeException {
    public NotSupportedCityName(String cityName) {
        super(String.format("City '%s' is not supported!", cityName));
    }
}
