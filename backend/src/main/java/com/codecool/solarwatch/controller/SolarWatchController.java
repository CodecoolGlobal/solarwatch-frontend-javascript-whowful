package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.*;
import com.codecool.solarwatch.model.dto.SolarWatchDTO;
import com.codecool.solarwatch.service.SolarWatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class SolarWatchController {

    private final SolarWatchService solarWatchService;

    public SolarWatchController(SolarWatchService solarWatchService) {
        this.solarWatchService = solarWatchService;
    }

    @GetMapping("/solarwatch")
    public ResponseEntity<?> getSolarWatch(@RequestParam(defaultValue = "Budapest") String city, @RequestParam LocalDate date){
        if (date.isBefore(LocalDate.now())){
            return ResponseEntity.badRequest().body("Date cannot be in the past");
        }
        SolarWatch report = solarWatchService.getSolarWatchForCity(city, date);
        return ResponseEntity.ok(report);
    }

    @PostMapping("/admin/solarwatch")
    public boolean addSolarWatch(@RequestBody SolarWatchDTO solarWatch){
        return solarWatchService.saveCityToDb(solarWatch.city(), solarWatch.date());
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<?> updateSolarWatch(@PathVariable Long id){
        return null;
    }

    @DeleteMapping("/admin/city/{id}")
    public boolean deleteCity(@PathVariable Long id){
        return solarWatchService.deleteCity(id);
    }

    @DeleteMapping("/admin/solardata/{id}")
    public boolean deleteSolarData(@PathVariable Long id){
        return solarWatchService.deleteSolarData(id);
    }
}
