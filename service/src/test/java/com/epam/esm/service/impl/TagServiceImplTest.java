package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagDaoImpl tagDao = Mockito.mock(TagDaoImpl.class);

    @InjectMocks
    private TagServiceImpl tagService;

    private static final Tag TAG_1 = new Tag(1, "tagName1");
    private static final Tag TAG_2 = new Tag(2, "tagName3");
    private static final Tag TAG_3 = new Tag(3, "tagName5");
    private static final Tag TAG_4 = new Tag(4, "tagName4");
    private static final Tag TAG_5 = new Tag(5, "tagName2");

    @Test
    public void testGetById() throws DaoException, IncorrectParameterException {
        when(tagDao.getById(TAG_3.getId())).thenReturn(TAG_3);
        Tag actual = tagService.getById(TAG_3.getId());
        Tag expected = TAG_3;

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() throws DaoException, IncorrectParameterException {
        List<Tag> tags = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5);
        when(tagDao.getAll()).thenReturn(tags);
        List<Tag> actual = tagService.getAll();
        List<Tag> expected = tags;

        assertEquals(expected, actual);
    }


    @Test
    public void testGetByName() throws DaoException, IncorrectParameterException {
        when(tagDao.getByName(TAG_3.getName())).thenReturn(TAG_3);
        Tag actual = tagService.getByName(TAG_3.getName());
        Tag expected = TAG_3;

        assertEquals(expected, actual);
    }

    @Test
    public void testInsert() throws DaoException, IncorrectParameterException {
        doNothing().when(tagDao).insert(TAG_3);
        tagService.insert(TAG_3);
        verify(tagDao, times(1)).insert(TAG_3);
    }
    @Test
    public void testInsertWithThrow() throws DaoException {
        doThrow(DaoException.class).when(tagDao).insert(any());
        assertThrows(DaoException.class, () -> tagService.insert(TAG_3));
        verify(tagDao, times(1)).insert(TAG_3);
    }
}