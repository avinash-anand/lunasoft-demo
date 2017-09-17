package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.dto.RunwayIdentificationDto;

public class RunwayIdentRowMapper implements RowMapper<RunwayIdentificationDto> {

    private static final String RUNWAY_IDENT = "IDENT";
    private static final String COUNT = "IDENT_COUNT";

    @Override
    public RunwayIdentificationDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        RunwayIdentificationDto dto = new RunwayIdentificationDto();
        dto.setRunwayIdentification(rs.getString(RUNWAY_IDENT));
        dto.setCount(rs.getInt(COUNT));
        return dto;
    }

}
