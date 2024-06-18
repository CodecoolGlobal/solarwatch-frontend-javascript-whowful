package com.codecool.solarwatch.model.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SolarWatchReportResult(String sunrise, String sunset) {
}
