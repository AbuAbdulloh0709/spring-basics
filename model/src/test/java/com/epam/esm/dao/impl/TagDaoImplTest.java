package com.epam.esm.dao.impl;

import com.epam.esm.config.DaoConfigTest;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = DaoConfigTest.class)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@Transactional
public class TagDaoImplTest {
    private static final long NOT_EXISTED_ID = 999L;
    private final Tag TAG_1 = new Tag(1, "tagName1");
    private final Tag TAG_2 = new Tag(2, "tagName3");
    private final Tag TAG_3 = new Tag(3, "tagName5");
    private final Tag TAG_4 = new Tag(4, "tagName4");
    private final Tag TAG_5 = new Tag(5, "tagName2");
    private static final String PART_OF_TAG_NAME = "tagName";
    private static final String NOT_EXISTED_NAME = "not existed name";
    private static final String INCORRECT_FILTER_PARAM = "incorrectParameter";
    private static final String INCORRECT_FILTER_PARAM_VALUE = "incorrectParameterValue";
    private static final String ASCENDING = "ASC";
    private final Pageable pageRequest = PageRequest.of(0, 5);

    @Autowired
    TagDao tagDao;

    @Test
    void getById() {
        Optional<Tag> expected = Optional.of(TAG_1);
        Optional<Tag> actual = tagDao.getById(TAG_1.getId());
        assertEquals(expected, actual);
    }

    @Test
    void getByNotExistedId() {
        Optional<Tag> actual = tagDao.getById(NOT_EXISTED_ID);
        assertFalse(actual.isPresent());
    }

    @Test
    void getAll() {
        List<Tag> actual = tagDao.getAll(pageRequest);
        List<Tag> expected = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5);
        assertEquals(expected, actual);
    }

    @Test
    void getByName() {
        Optional<Tag> expected = Optional.of(TAG_3);
        Optional<Tag> actual = tagDao.getByName(TAG_3.getName());
        assertEquals(expected, actual);
    }

    @Test
    void getByNotExistedName() {
        Optional<Tag> actual = tagDao.getByName(NOT_EXISTED_NAME);
        assertFalse(actual.isPresent());
    }

    @Test
    void getMostPopularTagWithHighestCostOfAllOrders() {
        Optional<Tag> expected = Optional.of(TAG_2);
        Optional<Tag> actual = tagDao.getMostPopularTagWithHighestCostOfAllOrders();
        assertEquals(expected, actual);
    }
}