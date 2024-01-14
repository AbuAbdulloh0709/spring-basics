package com.epam.esm.dao.impl;

import com.epam.esm.config.DaoConfigTest;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.entity.Role.ADMIN;
import static com.epam.esm.entity.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = DaoConfigTest.class)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserDaoImplTest {
    private static final long NOT_EXISTED_ID = 999L;
    private static final User USER_1 = new User(1, "name1", "email1@email.com",
            "$2a$10$zFdd9cCOS/A6./WuG7etC.Mhn3M.R4u2VFMVlz4cVSiNK6bLaT7S.", ADMIN);
    private static final User USER_2 = new User(2, "name2", "email2@email.com",
            "$2a$10$gwocSNWrBeUOO.0.0./iveTo/uDatwYEWHadx1xMUZ.Do93yS9kc2", USER);

    private final Pageable pageRequest = PageRequest.of(0, 5);

    @Autowired
    UserDao userDao;

    @Test
    void getById() {
        Optional<User> expected = Optional.of(USER_1);
        Optional<User> actual = userDao.getById(USER_1.getId());
        assertEquals(expected, actual);
    }

    @Test
    void getByNotExistedId() {
        Optional<User> actual = userDao.getById(NOT_EXISTED_ID);
        assertFalse(actual.isPresent());
    }

    @Test
    void getAll() {
        List<User> expected = Arrays.asList(USER_1, USER_2);
        List<User> actual = userDao.getAll(pageRequest);
        assertEquals(expected, actual);
    }
}