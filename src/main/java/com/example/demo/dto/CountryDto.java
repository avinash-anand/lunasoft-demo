package com.example.demo.dto;

import lombok.Data;

@Data
public class CountryDto {
    private Long id;
    private String code;
    private String name;
    private String continent;
    private String wikipediaLink;
    private String keywords;
}
