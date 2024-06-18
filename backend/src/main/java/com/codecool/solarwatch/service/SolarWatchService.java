package com.codecool.solarwatch.service;

import com.codecool.solarwatch.exception.NotSupportedCityName;
import com.codecool.solarwatch.model.*;
import com.codecool.solarwatch.model.report.GeocodeReport;
import com.codecool.solarwatch.model.report.SolarWatchReport;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SolarDataRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SolarWatchService {
    private static final String API_KEY = "55d7b2bae7a7cd01cf22089a1bd93ca6";
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(SolarWatchService.class);
    private final CityRepository cityRepository;
    private final SolarDataRepository solarDataRepository;

    public SolarWatchService(WebClient webClient, CityRepository cityRepository, SolarDataRepository solarDataRepository) {
        this.webClient = webClient;
        this.cityRepository = cityRepository;
        this.solarDataRepository = solarDataRepository;
    }

    @Transactional
    public boolean deleteSolarData(Long id) {
        if (!solarDataRepository.existsById(id)) {
            return false;
        }
        solarDataRepository.deleteById(id);
        return true;
    }

    public boolean deleteCity(Long id) {
        if (!cityRepository.existsById(id)) {
            return false;
        }
        cityRepository.deleteById(id);
        return true;
    }

    public SolarWatch getSolarWatchForCity(String city, LocalDate date) {
        if (!cityRepository.existsByName(city)) {
            saveCityToDb(city, date);
        }
        City selectedCity = getCityByName(city);
        SolarData solarDataForSelectedCity = selectedCity.getSolarData().stream()
                .filter(solarData -> solarData.getDate().equals(date))
                .findFirst()
                .orElse(null);
        logger.info("SolarData selected {}", solarDataForSelectedCity);
        if (solarDataForSelectedCity == null) {
            SolarData newSolarData = createSolarData(selectedCity.getLatitude(), selectedCity.getLongitude(), date);
            solarDataRepository.save(newSolarData);

            List<SolarData> solarDataListForCity = selectedCity.getSolarData();
            solarDataListForCity.add(newSolarData);
            selectedCity.setSolarData(solarDataListForCity);
            cityRepository.save(selectedCity);

            return new SolarWatch(newSolarData.getSunrise(), newSolarData.getSunset(), city);
        }
        return new SolarWatch(solarDataForSelectedCity.getSunrise(), solarDataForSelectedCity.getSunset(), city);
    }


    public boolean saveCityToDb(String city, LocalDate date) {
        String geocodeUrl = String.format("https://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s", city, API_KEY);
        GeocodeReport[] geocodeObjects = webClient
                .get()
                .uri(geocodeUrl)
                .retrieve()
                .bodyToMono(GeocodeReport[].class)
                .block();

        if (geocodeObjects == null) {
            throw new NotSupportedCityName(city);
        }
        SolarData solarData = createSolarData(geocodeObjects[0].lat(), geocodeObjects[0].lon(), date);

        City newCity = new City();
        newCity.setCountry(geocodeObjects[0].country());
        newCity.setName(geocodeObjects[0].name());
        newCity.setLatitude(geocodeObjects[0].lat());
        newCity.setLongitude(geocodeObjects[0].lon());
        newCity.setState(geocodeObjects[0].state());
        newCity.setSolarData(List.of(solarData));
        solarData.setCity(newCity);
        cityRepository.save(newCity);
        logger.info("City saved to DB: {}", newCity.getName());
        return true;
    }

    private SolarData createSolarData(double latitude, double longitude, LocalDate date) {
        String solarWatchUrl = String.format("https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=%s", latitude, longitude, date);
        SolarWatchReport solarWatchResponse = webClient
                .get()
                .uri(solarWatchUrl)
                .retrieve()
                .bodyToMono(SolarWatchReport.class)
                .block();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm:ss a");
        String sunrise = solarWatchResponse.results().sunrise();
        String sunset = solarWatchResponse.results().sunset();

        SolarData solarData = new SolarData();
        solarData.setSunrise(LocalTime.parse(sunrise, formatter));
        solarData.setSunset(LocalTime.parse(sunset, formatter));
        solarData.setDate(date);
        return solarData;
    }

    private City getCityByName(String cityName) {
        return cityRepository.findByName(cityName).orElseThrow(() -> new NotSupportedCityName(cityName));
    }
}
