package com.example.demo.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {
    
    private static final int TEN = 10;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    static final String COUNTRY_QUERY = "select * from COUNTRIES c where c.code = ? or c.name = ? LIMIT 1";
    static final String AIRPORTS_RUNWAYS_QUERY = "SELECT a.id A_ID, a.ident A_IDENT, a.type A_TYPE, a.name A_NAME, a.gps_code A_GPS, a.local_code A_LOC, r.id R_ID, r.length_ft R_LEN, r.width_ft R_WID, r.surface R_SUR FROM airports a LEFT JOIN runways r ON a.id = r.airport_ref WHERE a.iso_country = ?";
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    static final String COUNTRY_AIRPORT_COUNT_QUERY = "select c.id, c.code, c.name, count(a.iso_country) as a_count from countries c left join airports a on c.code = a.iso_country group by c.code order by count(a.iso_country)";
    static final String RUNWAY_IDENTIFICATION_QUERY = "select r.le_ident ident, count(r.le_ident) ident_count from runways r group by r.le_ident order by count(r.le_ident) desc limit 10";
    static final String COUNTRY_WISE_RUNWAY_SURFACE_QUERY = "select distinct c.name, c.code, run_air.r_surf from countries c left join (select r.surface r_surf, a.iso_country a_iso from runways r inner join airports a on r.airport_ref = a.id) run_air on c.code = run_air.a_iso order by a_iso";
    
    @Override
    public QueryDto findCountryByNameOrCode(String nameOrCode) {
        log.debug("[CountryServiceImpl][findCountryByNameOrCode] - start");
        QueryDto dto = new QueryDto();
        Object[] args = new Object[TWO];
        args[ZERO] = nameOrCode;
        args[ONE] = nameOrCode;
        CountryDto c = jdbcTemplate.query(COUNTRY_QUERY, args, new CountryRsExtractor());
        if (c != null) {
            dto.setCountry(c);
            Object[] newArgs = new Object[ONE];
            newArgs[ZERO] = c.getCode();
            List<AirportAndRunwayDto> list = jdbcTemplate.query(AIRPORTS_RUNWAYS_QUERY, newArgs,
                    new AirportAndRunwayRowMapper());
            dto.setAirportRunwayData(list);
        }
        log.debug("[CountryServiceImpl][findCountryByNameOrCode] - end - dto = {}", dto);
        return dto;
    }

    @Override
    public ReportDto getCountryReport() {
        log.debug("[CountryServiceImpl][getCountryReport] - start");
        ReportDto dto = new ReportDto();
        List<CountryAirportCountDto> countryWiseAirportCount = jdbcTemplate.query(COUNTRY_AIRPORT_COUNT_QUERY,
                new CountryAirportCountRowMapper());
        List<RunwayIdentificationDto> commonRunwayIdent = jdbcTemplate.query(RUNWAY_IDENTIFICATION_QUERY,
                new RunwayIdentRowMapper());
        List<CountryWiseRunwaySurfaceDto> countryWiseRunwayList = jdbcTemplate.query(COUNTRY_WISE_RUNWAY_SURFACE_QUERY,
                new CountryWiseSurfaceRowMapper());
        if (countryWiseAirportCount != null && !countryWiseAirportCount.isEmpty()
                && countryWiseAirportCount.size() >= TEN) {
            dto.setCountryWithMinAirports(countryWiseAirportCount.subList(ZERO, TEN));
            dto.setCountryWithMaxAirports(countryWiseAirportCount.subList(countryWiseAirportCount.size() - TEN,
                    countryWiseAirportCount.size()));
            Collections.reverse(dto.getCountryWithMaxAirports());
        }
        dto.setMostCommonRunwayIdent(commonRunwayIdent);
        if (null != countryWiseRunwayList && !countryWiseRunwayList.isEmpty()) {
            Map<String, List<CountryWiseRunwaySurfaceDto>> countryWiseSurfaceMap = countryWiseRunwayList.stream()
                    .collect(Collectors.groupingBy(CountryWiseRunwaySurfaceDto::getCountryName));
            dto.setCountryWiseSurface(countryWiseSurfaceMap);
        }
        log.debug("[CountryServiceImpl][getCountryReport] - end - dto = {}", dto);
        return dto;
    }

}
