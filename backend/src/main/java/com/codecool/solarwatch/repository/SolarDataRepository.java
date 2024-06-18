package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.SolarData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface SolarDataRepository extends JpaRepository<SolarData, Long> {
    boolean existsByDate(LocalDate date);
}
