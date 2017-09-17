package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.dto.CountryWiseRunwaySurfaceDto;

public class CountryWiseSurfaceRowMapper implements RowMapper<CountryWiseRunwaySurfaceDto> {

    private static final String COUNTRY_NAME = "NAME";
    private static final String COUNTRY_CODE = "CODE";
    private static final String RUNWAY_SURFACE = "R_SURF";
    private static final String NO_DATA_AVAILABLE = "NO DATA AVAILABLE";

    @Override
    public CountryWiseRunwaySurfaceDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        CountryWiseRunwaySurfaceDto dto = new CountryWiseRunwaySurfaceDto();
        dto.setCountryName(rs.getString(COUNTRY_NAME));
        dto.setCountryCode(rs.getString(COUNTRY_CODE));
        if (rs.getString(RUNWAY_SURFACE) != null) {
            dto.setRunwaySurface(rs.getString(RUNWAY_SURFACE));
        } else {
            dto.setRunwaySurface(NO_DATA_AVAILABLE);
        }
        return dto;
    }

}
