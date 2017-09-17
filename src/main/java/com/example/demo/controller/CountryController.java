package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.QueryDto;
import com.example.demo.dto.ReportDto;
import com.example.demo.service.CountryService;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.MediaType.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@Slf4j
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping(value = "/countries", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QueryDto> getCountryByCodeOrName(@RequestParam("q") String q) {
        log.debug("query param - {}", q);
        return ResponseEntity.ok(countryService.findCountryByNameOrCode(q));
    }
    
    @GetMapping(value = "/country-report", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReportDto> generateReportData() {
        log.debug("generate report data called");
        return ResponseEntity.ok(countryService.getCountryReport());
    }

}
