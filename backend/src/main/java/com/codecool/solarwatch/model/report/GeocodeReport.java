package com.codecool.solarwatch.model.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = false)
public record GeocodeReport(double lat, double lon, String country, String state, String name) {
}
