package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;

public interface TagDAO extends BaseDAO<Tag> {
    Tag getByName(String name) throws DaoException;
}
