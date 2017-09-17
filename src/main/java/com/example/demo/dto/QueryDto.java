package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class QueryDto {
    private CountryDto country;
    private List<AirportAndRunwayDto> airportRunwayData;
}
