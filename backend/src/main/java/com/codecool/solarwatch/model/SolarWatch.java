package com.codecool.solarwatch.model;

import java.time.LocalTime;

public record SolarWatch(LocalTime sunrise, LocalTime sunset, String city) {
}
