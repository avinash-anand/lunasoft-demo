package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.QueryDto;
import com.example.demo.dto.ReportDto;
import com.example.demo.service.CountryService;

@RunWith(MockitoJUnitRunner.class)
public class CountryControllerTest {
    
    @InjectMocks
    private CountryController countryController;
    @Mock
    private CountryService countryService;
    @Spy
    private QueryDto queryDto;
    @Spy
    private ReportDto reportDto;
    
    
    @Before
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        when(countryService.findCountryByNameOrCode(Matchers.anyString())).thenReturn(queryDto);
        when(countryService.getCountryReport()).thenReturn(reportDto);
    }
    
    @Test
    public void getCountryByCodeOrNameTest() {
        ResponseEntity<QueryDto> result = countryController.getCountryByCodeOrName("IN");
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
    @Test
    public void generateReportDataTest() {
        ResponseEntity<ReportDto> result = countryController.generateReportData();
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    
}
