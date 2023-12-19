package com.epam.esm.dao;


import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.db_config.DatabaseConfigTest;
import com.epam.esm.dto.FilterRequest;
import com.epam.esm.dto.Sorting;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = DatabaseConfigTest.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class GiftCertificatesDaoImplTest {


    @Autowired
    private GiftCertificateDaoImpl giftCertificate;

    private static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1, "giftCertificate1",
            "description1", new BigDecimal("99.90"), 1, "2020-10-20T07:20:15.156",
            "2020-10-20T07:20:15.156",
            Arrays.asList(new Tag(1, "tagName1"), new Tag(2, "tagName2")));

    private static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2, "giftCertificate3",
            "description3", new BigDecimal("100.99"), 3, "2019-10-20T07:20:15.156",
            "2019-10-20T07:20:15.156",
            Collections.singletonList(new Tag(3, "tagName3")));

    private static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3, "giftCertificate21",
            "description2", new BigDecimal("999.99"), 2, "2018-10-20T07:20:15.156",
            "2018-10-20T07:20:15.156",
            Collections.emptyList());


    @Test
    void testGetById() throws DaoException {
        GiftCertificate actual = giftCertificate.getById(GIFT_CERTIFICATE_2.getId());
        GiftCertificate expected = GIFT_CERTIFICATE_2;

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() throws DaoException {
        List<GiftCertificate> actual = giftCertificate.getAll();
        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetWithFilters() throws DaoException {
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setName("1");
        filterRequest.setSorting(new Sorting("date", "desc"));

        List<GiftCertificate> actual = giftCertificate.doFilter(filterRequest);

        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_1,GIFT_CERTIFICATE_3);

        assertEquals(expected, actual);
    }

}