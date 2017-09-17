package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.dto.CountryAirportCountDto;

public class CountryAirportCountRowMapper implements RowMapper<CountryAirportCountDto> {

    private static final String ID = "ID";
    private static final String CODE = "CODE";
    private static final String NAME = "NAME";
    private static final String COUNT = "A_COUNT";
    
    @Override
    public CountryAirportCountDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        CountryAirportCountDto dto = new CountryAirportCountDto();
        dto.setId(rs.getLong(ID));
        dto.setCode(rs.getString(CODE));
        dto.setName(rs.getString(NAME));
        dto.setAirportCount(rs.getInt(COUNT));
        return dto;
    }

}
