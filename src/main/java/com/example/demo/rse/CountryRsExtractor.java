package com.example.demo.rse;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.example.demo.dto.CountryDto;

public class CountryRsExtractor implements ResultSetExtractor<CountryDto> {

    private static final String ID = "ID";
    private static final String CODE = "CODE";
    private static final String NAME = "NAME";
    private static final String CONTINENT = "CONTINENT";
    private static final String WIKI_LINK = "WIKIPEDIA_LINK";
    private static final String KEYWORDS = "KEYWORDS";
    @Override
    public CountryDto extractData(ResultSet rs) throws SQLException, DataAccessException {
        if(rs.next()) {
            CountryDto c = new CountryDto();
            c.setId(rs.getLong(ID));
            c.setName(rs.getString(NAME));
            c.setCode(rs.getString(CODE));
            c.setContinent(rs.getString(CONTINENT));
            c.setWikipediaLink(rs.getString(WIKI_LINK));
            c.setKeywords(rs.getString(KEYWORDS));
            return c;
        }
        return null;
    }


}
