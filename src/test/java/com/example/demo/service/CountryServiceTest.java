package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.example.demo.dto.AirportAndRunwayDto;
import com.example.demo.dto.CountryAirportCountDto;
import com.example.demo.dto.CountryDto;
import com.example.demo.dto.CountryWiseRunwaySurfaceDto;
import com.example.demo.dto.QueryDto;
import com.example.demo.dto.ReportDto;
import com.example.demo.dto.RunwayIdentificationDto;
import com.example.demo.mapper.AirportAndRunwayRowMapper;
import com.example.demo.mapper.CountryAirportCountRowMapper;
import com.example.demo.mapper.CountryWiseSurfaceRowMapper;
import com.example.demo.mapper.RunwayIdentRowMapper;
import com.example.demo.rse.CountryRsExtractor;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTest {

    @InjectMocks
    private CountryServiceImpl countryService;
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Spy
    private CountryDto countryDto;
    @Spy
    private QueryDto queryDto;
    @Spy
    private ArrayList<AirportAndRunwayDto> airportAndRunwayDataList;
    @Spy
    private ReportDto reportDto;
    @Spy
    private ArrayList<CountryAirportCountDto> countryAirportCountList;
    @Spy
    private ArrayList<CountryWiseRunwaySurfaceDto> countryWiseRunwayList;
    @Spy
    private ArrayList<RunwayIdentificationDto> runwayIdentificationList;

    private static final String IN = "IN";

    @Before
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        populateQueryDto();
        populateCountryAirportCountList();
        populateCountryWiseRunwayList();
        when(jdbcTemplate.query(Matchers.eq(CountryServiceImpl.COUNTRY_QUERY), Matchers.<Object[]>any(),
                Matchers.any(CountryRsExtractor.class))).thenReturn(countryDto);
        when(jdbcTemplate.query(Matchers.eq(CountryServiceImpl.AIRPORTS_RUNWAYS_QUERY), Matchers.<Object[]>any(),
                Matchers.any(AirportAndRunwayRowMapper.class))).thenReturn(airportAndRunwayDataList);

        when(jdbcTemplate.query(Matchers.eq(CountryServiceImpl.COUNTRY_AIRPORT_COUNT_QUERY),
                Matchers.any(CountryAirportCountRowMapper.class))).thenReturn(countryAirportCountList);
        when(jdbcTemplate.query(Matchers.eq(CountryServiceImpl.COUNTRY_WISE_RUNWAY_SURFACE_QUERY),
                Matchers.any(CountryWiseSurfaceRowMapper.class))).thenReturn(countryWiseRunwayList);
        when(jdbcTemplate.query(Matchers.eq(CountryServiceImpl.RUNWAY_IDENTIFICATION_QUERY),
                Matchers.any(RunwayIdentRowMapper.class))).thenReturn(runwayIdentificationList);
    }

    private void populateQueryDto() {
        queryDto.setCountry(countryDto);
        queryDto.setAirportRunwayData(airportAndRunwayDataList);
    }

    private void populateCountryAirportCountList() {
        countryAirportCountList.addAll(Arrays.asList(new CountryAirportCountDto(), new CountryAirportCountDto(),
                new CountryAirportCountDto(), new CountryAirportCountDto(), new CountryAirportCountDto(),
                new CountryAirportCountDto(), new CountryAirportCountDto(), new CountryAirportCountDto(),
                new CountryAirportCountDto(), new CountryAirportCountDto()));
    }

    private void populateCountryWiseRunwayList() {
        CountryWiseRunwaySurfaceDto dto1 = new CountryWiseRunwaySurfaceDto();
        dto1.setCountryCode("IN");
        dto1.setCountryName("INDIA");
        dto1.setRunwaySurface("S1");
        CountryWiseRunwaySurfaceDto dto2 = new CountryWiseRunwaySurfaceDto();
        dto2.setCountryCode("IN");
        dto2.setCountryName("INDIA");
        dto2.setRunwaySurface("S2");
        countryWiseRunwayList.addAll(Arrays.asList(dto1, dto2));
    }

    @Test
    public void findCountryByNameOrCode_whenQueryReturnsData_thenPopulateQueryDto() {
        QueryDto result = countryService.findCountryByNameOrCode(IN);
        assertThat(result).isNotNull();
        assertThat(result.getCountry()).isEqualTo(countryDto);
    }

    @Test
    public void findCountryByNameOrCode_whenQueryReturnsNull_thenDontPopulateQueryDto() {
        when(jdbcTemplate.query(Matchers.anyString(), Matchers.<Object[]>any(),
                Matchers.<ResultSetExtractor<CountryDto>>any())).thenReturn(null);
        QueryDto result = countryService.findCountryByNameOrCode(IN);
        assertThat(result).isNotNull();
        assertThat(result.getCountry()).isNull();
    }

    @Test
    public void getCountryReport_whenAllDataComes_thenPopulateDtoProperly() {
        ReportDto result = countryService.getCountryReport();
        assertThat(result).isNotNull();
        assertThat(result.getMostCommonRunwayIdent()).isNotNull();
        Map<String, List<CountryWiseRunwaySurfaceDto>> expectedMap = new HashMap<>();
        CountryWiseRunwaySurfaceDto dto1 = new CountryWiseRunwaySurfaceDto();
        dto1.setCountryCode("IN");
        dto1.setCountryName("INDIA");
        dto1.setRunwaySurface("S1");
        CountryWiseRunwaySurfaceDto dto2 = new CountryWiseRunwaySurfaceDto();
        dto2.setCountryCode("IN");
        dto2.setCountryName("INDIA");
        dto2.setRunwaySurface("S2");
        expectedMap.put("INDIA", Arrays.asList(dto1, dto2));
        assertThat(result.getCountryWiseSurface()).isEqualTo(expectedMap);
        assertThat(result.getCountryWithMaxAirports()).isEqualTo(countryAirportCountList);
        assertThat(result.getCountryWithMinAirports()).isEqualTo(countryAirportCountList);
        assertThat(result.getMostCommonRunwayIdent()).isEqualTo(runwayIdentificationList);
    }

    @Test
    public void getCountryReport_whenAirportNRunwayCountNull_thenDontPopulateDto() {
        when(jdbcTemplate.query(Matchers.eq(CountryServiceImpl.COUNTRY_AIRPORT_COUNT_QUERY),
                Matchers.any(CountryAirportCountRowMapper.class))).thenReturn(null);
        when(jdbcTemplate.query(Matchers.eq(CountryServiceImpl.COUNTRY_WISE_RUNWAY_SURFACE_QUERY),
                Matchers.any(CountryWiseSurfaceRowMapper.class))).thenReturn(null);
        ReportDto result = countryService.getCountryReport();
        assertThat(result).isNotNull();
        assertThat(result.getMostCommonRunwayIdent()).isNotNull();
        assertThat(result.getCountryWiseSurface()).isNull();
        assertThat(result.getCountryWithMaxAirports()).isNull();
        assertThat(result.getCountryWithMinAirports()).isNull();
        assertThat(result.getMostCommonRunwayIdent()).isEqualTo(runwayIdentificationList);
    }

}
