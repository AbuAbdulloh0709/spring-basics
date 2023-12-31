package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @Mock
    private TagDaoImpl tagDao = Mockito.mock(TagDaoImpl.class);

    @Mock
    private UserDaoImpl userDao = Mockito.mock(UserDaoImpl.class);

    @InjectMocks
    private TagServiceImpl tagService;

    private static final Tag TAG_1 = new Tag(1, "tagName1");
    private static final Tag TAG_2 = new Tag(2, "tagName3");
    private static final Tag TAG_3 = new Tag(3, "tagName5");
    private static final Tag TAG_4 = new Tag(4, "tagName4");
    private static final Tag TAG_5 = new Tag(5, "tagName2");

    private static final User USER_1 = new User(1, "user1");

    private static final int PAGE = 0;
    private static final int SIZE = 5;

    @Test
    public void testGetById() {
        when(tagDao.getById(TAG_3.getId())).thenReturn(Optional.of(TAG_3));

        Tag actual = tagService.getById(TAG_3.getId());
        Tag expected = TAG_3;

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() {
        List<Tag> tags = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5);
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        when(tagDao.getAll(pageRequest)).thenReturn(tags);

        List<Tag> actual = tagService.getAll(PAGE, SIZE);
        List<Tag> expected = tags;

        assertEquals(expected, actual);
    }

    @Test
    public void testInsert() {
        doReturn(TAG_1).when(tagDao).insert(any(Tag.class));

        Tag insertableTag = new Tag();
        insertableTag.setName(TAG_1.getName());
        Tag actual = tagService.insert(insertableTag);
        Tag expected = TAG_1;

        assertEquals(expected, actual);
    }

    @Test
    public void testGetMostPopularTagOfUserWithHighestCostOfAllOrders() {
        when(tagDao.getMostPopularTagsWithHighestCostOfAllOrders(USER_1.getId())).thenReturn(List.of(TAG_1));
        List<Tag> actual = tagService.getMostPopularTagsOfUserWithHighestCostOfAllOrders(USER_1.getId());

        assertEquals(List.of(TAG_1), actual);
    }
}