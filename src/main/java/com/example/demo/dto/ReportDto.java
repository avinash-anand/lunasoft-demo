package com.example.demo.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ReportDto {
    List<CountryAirportCountDto> countryWithMaxAirports;
    List<CountryAirportCountDto> countryWithMinAirports;
    List<RunwayIdentificationDto> mostCommonRunwayIdent;
    Map<String, List<CountryWiseRunwaySurfaceDto>> countryWiseSurface;
}
