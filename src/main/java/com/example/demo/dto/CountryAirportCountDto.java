package com.example.demo.dto;

import lombok.Data;

@Data
public class CountryAirportCountDto {
    private Long id;
    private String code;
    private String name;
    private Integer airportCount;
}
