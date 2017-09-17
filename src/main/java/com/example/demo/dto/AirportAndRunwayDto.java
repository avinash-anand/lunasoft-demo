package com.example.demo.dto;

import lombok.Data;

@Data
public class AirportAndRunwayDto {

    private Long airportId;
    private String airportIdentity;
    private String airportType;
    private String airportName;
    private String airportGpsCode;
    private String airportLocalCode;
    private Long runwayId;
    private Integer runwayLengthFt;
    private Integer runwayWidthFt;
    private String runwaySurface;

}
