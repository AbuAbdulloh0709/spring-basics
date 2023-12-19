package com.epam.esm.dao;


import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.db_config.DatabaseConfigTest;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration(classes = DatabaseConfigTest.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TagDaoImplTest {


    @Autowired
    private TagDaoImpl tagDao;

    private static final Tag TAG_1 = new Tag(1, "tagName1");
    private static final Tag TAG_2 = new Tag(2, "tagName2");
    private static final Tag TAG_3 = new Tag(3, "tagName3");
    private static final Tag TAG_4 = new Tag(4, "tagName4");
    private static final Tag TAG_5 = new Tag(5, "tagName5");


    @Test
    @Order(1)
    public void testGetAll() throws DaoException {
        List<Tag> actual = tagDao.getAll();
        List<Tag> expected = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5);

        assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    public void testGetById() throws DaoException {
        Tag actual = tagDao.getById(TAG_3.getId());
        Tag expected = TAG_3;

        assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    public void testGetByName() throws DaoException {
        Tag actual = tagDao.getByName(TAG_2.getName());

        assertEquals(TAG_2, actual);
    }

    @Test
    @Order(4)
    public void testRemoveById() throws DaoException {
        tagDao.removeById(TAG_3.getId());
        assertThrows(DaoException.class, () -> tagDao.getById(TAG_3.getId()));
    }

}