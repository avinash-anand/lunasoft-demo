package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.dto.AirportAndRunwayDto;

public class AirportAndRunwayRowMapper implements RowMapper<AirportAndRunwayDto> {
    
    private static final String AIRPORT_ID = "A_ID";
    private static final String AIRPORT_IDENT = "A_IDENT";
    private static final String AIRPORT_TYPE = "A_TYPE";
    private static final String AIRPORT_NAME = "A_NAME";
    private static final String AIRPORT_GPS = "A_GPS";
    private static final String AIRPORT_LOC = "A_LOC";
    private static final String RUNWAY_ID = "R_ID";
    private static final String RUNWAY_LEN = "R_LEN";
    private static final String RUNWAY_WID = "R_WID";
    private static final String RUNWAY_SURFACE = "R_SUR";

    @Override
    public AirportAndRunwayDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        AirportAndRunwayDto dto = new AirportAndRunwayDto();
        dto.setAirportId(rs.getLong(AIRPORT_ID));
        if(isNotNullOrEmpty(rs.getString(AIRPORT_IDENT))) dto.setAirportIdentity(rs.getString(AIRPORT_IDENT));
        dto.setAirportType(rs.getString(AIRPORT_TYPE));
        dto.setAirportName(rs.getString(AIRPORT_NAME));
        if(isNotNullOrEmpty(rs.getString(AIRPORT_GPS))) dto.setAirportGpsCode(rs.getString(AIRPORT_GPS));
        if(isNotNullOrEmpty(rs.getString(AIRPORT_LOC))) dto.setAirportLocalCode(rs.getString(AIRPORT_LOC));
        if(isNotNullOrEmpty(rs.getString(RUNWAY_ID))) dto.setRunwayId(rs.getLong(RUNWAY_ID));
        if(isNotNullOrEmpty(rs.getString(RUNWAY_LEN))) dto.setRunwayLengthFt(rs.getInt(RUNWAY_LEN));
        if(isNotNullOrEmpty(rs.getString(RUNWAY_WID))) dto.setRunwayWidthFt(rs.getInt(RUNWAY_WID));
        if(isNotNullOrEmpty(rs.getString(RUNWAY_SURFACE))) dto.setRunwaySurface(rs.getString(RUNWAY_SURFACE));
        return dto;
    }
    
    private static boolean isNotNullOrEmpty(String s) {
            return s != null && !s.trim().isEmpty();
    }

}
