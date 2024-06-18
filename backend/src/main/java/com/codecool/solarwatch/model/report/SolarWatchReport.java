package com.codecool.solarwatch.model.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SolarWatchReport(SolarWatchReportResult results) {
}
