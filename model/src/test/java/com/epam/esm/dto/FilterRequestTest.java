package com.epam.esm.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FilterRequestTest {

    @Test
    public void testGenerateSqlQuery() {
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setName("TestName");
        filterRequest.setDescription("TestDescription");

        Sorting sorting = new Sorting();
        sorting.setSortBy("date");
        sorting.setSortOrder("asc");
        filterRequest.setSorting(sorting);

        String sqlQuery = filterRequest.generateSqlQuery();

        String expectedSqlQuery = "SELECT * FROM gift_certificates gc where name ILIKE '%TestName%' and description ILIKE '%TestDescription%'  order by create_date asc";
        assertEquals(expectedSqlQuery, sqlQuery.trim());
    }

    @Test
    public void testGenerateSqlQueryWithoutSorting() {
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setName("TestName");
        filterRequest.setDescription("TestDescription");

        String sqlQuery = filterRequest.generateSqlQuery();

        String expectedSqlQuery = "SELECT * FROM gift_certificates gc where name ILIKE '%TestName%' and description ILIKE '%TestDescription%'";
        assertEquals(expectedSqlQuery, sqlQuery.trim());
    }
}
