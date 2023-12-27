package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.handler.DateHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDaoImpl giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);

    @Mock
    private DateHandler dateHandler = Mockito.mock(DateHandler.class);
    private static final LocalDateTime UPDATED_DATE = LocalDateTime.parse("2023-08-29T06:12:15.156");

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    private static final Tag TAG_2 = new Tag(2, "tagName3");

    private static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1, "giftCertificate1",
            "description1", new BigDecimal("10.1"), 1, LocalDateTime.parse("2023-08-29T06:12:15.156"),
            LocalDateTime.parse("2023-08-29T06:12:15.156"), Arrays.asList(new Tag(1, "tagName1"),
            new Tag(2, "tagName3"), new Tag(3, "tagName5")));

    private static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2, "giftCertificate3",
            "description3", new BigDecimal("30.3"), 3, LocalDateTime.parse("2021-08-29T06:12:15.156"),
            LocalDateTime.parse("2021-08-29T06:12:15.156"), Collections.singletonList(new Tag(2, "tagName3")));

    private static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3, "giftCertificate2",
            "description2", new BigDecimal("20.2"), 2, LocalDateTime.parse("2022-08-29T06:12:15.156"),
            LocalDateTime.parse("2022-08-29T06:12:15.156"), null);

    private static final String SORT_PARAMETER = "DESC";
    private static final int PAGE = 0;
    private static final int SIZE = 5;

    @Test
    void testGetById() {
        when(giftCertificateDao.getById(GIFT_CERTIFICATE_2.getId())).thenReturn(Optional.of(GIFT_CERTIFICATE_2));
        GiftCertificate actual = giftCertificateService.getById(GIFT_CERTIFICATE_2.getId());
        GiftCertificate expected = GIFT_CERTIFICATE_2;

        assertEquals(expected, actual);
    }

    @Test
    void testGetAll() {
        List<GiftCertificate> giftCertificates = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        when(giftCertificateDao.getAll(pageRequest)).thenReturn(giftCertificates);
        List<GiftCertificate> actual = giftCertificateService.getAll(PAGE, SIZE);

        List<GiftCertificate> expected = giftCertificates;
        assertEquals(expected, actual);
    }

    @Test
    void testInsert() {
        GiftCertificate insertableGiftCertificate = new GiftCertificate();
        insertableGiftCertificate.setName(GIFT_CERTIFICATE_3.getName());
        insertableGiftCertificate.setTags(GIFT_CERTIFICATE_3.getTags());
        insertableGiftCertificate.setLastUpdateDate(GIFT_CERTIFICATE_3.getLastUpdateDate());
        insertableGiftCertificate.setDescription(GIFT_CERTIFICATE_3.getDescription());
        insertableGiftCertificate.setCreateDate(GIFT_CERTIFICATE_3.getCreateDate());
        insertableGiftCertificate.setDuration(GIFT_CERTIFICATE_3.getDuration());
        insertableGiftCertificate.setPrice(GIFT_CERTIFICATE_3.getPrice());

        when(dateHandler.getCurrentDate()).thenReturn(UPDATED_DATE);
        doReturn(GIFT_CERTIFICATE_3).when(giftCertificateDao).insert(any());

        GiftCertificate actual = giftCertificateService.insert(insertableGiftCertificate);
        GiftCertificate expected = GIFT_CERTIFICATE_3;

        assertEquals(expected, actual);
    }

    @Test
    void testDoFilter() {
        List<GiftCertificate> giftCertificates = Arrays.asList(GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_1);
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("tag_name", TAG_2.getName());
        requestParams.add("sortByName", SORT_PARAMETER);
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        when(giftCertificateDao.doFilter(requestParams, pageRequest)).thenReturn(giftCertificates);

        List<GiftCertificate> actual = giftCertificateService.doFilter(requestParams, PAGE, SIZE);
        List<GiftCertificate> expected = giftCertificates;

        assertEquals(expected, actual);
    }
}