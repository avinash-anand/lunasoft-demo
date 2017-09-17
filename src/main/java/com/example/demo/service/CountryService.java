package com.example.demo.service;

import com.example.demo.dto.QueryDto;
import com.example.demo.dto.ReportDto;

public interface CountryService {
    
    QueryDto findCountryByNameOrCode(String nameOrCode);
    ReportDto getCountryReport();
    
}
