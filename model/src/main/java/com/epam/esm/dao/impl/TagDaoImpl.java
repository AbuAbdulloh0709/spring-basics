package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.epam.esm.exception.ExceptionDaoMessageCodes.*;

@Repository
public class TagDaoImpl implements TagDAO {

    private final String QUERY_CREATE_TAG = "insert into tags (tag_name) values(?)";
    private final String QUERY_GET_TAG = "select * from tags where id = ?;";
    private final String QUERY_GET_TAG_ALL = "select * from tags;";
    private final String QUERY_GET_BY_NAME = "select * from tags where tag_name = ?";
    private final String QUERY_DELETE_BY_ID = "delete from tags where id = ?";


    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
    }

    @Override
    public void insert(Tag tag) throws DaoException {
        try {
            jdbcTemplate.update(QUERY_CREATE_TAG, tag.getName());
        } catch (DataAccessException e) {
            System.out.println(e.getLocalizedMessage());
            throw new DaoException(SAVING_ERROR);
        }
    }

    @Override
    public Tag getById(long id) throws DaoException {
        List<Tag> tags = jdbcTemplate.query(QUERY_GET_TAG, tagMapper, id);
        if (tags.size() != 0) {
            return tags.get(0);
        } else {
            throw new DaoException(NO_ENTITY_WITH_ID);
        }
    }

    @Override
    public List<Tag> getAll() throws DaoException {
        try {
            return jdbcTemplate.query(QUERY_GET_TAG_ALL, tagMapper);
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY);
        }
    }

    @Override
    public void removeById(long id) throws DaoException {
        try {
            jdbcTemplate.update(QUERY_DELETE_BY_ID, id);
        } catch (DataAccessException e) {
            System.out.println(e.getLocalizedMessage());
            throw new DaoException(SAVING_ERROR);
        }
    }
//
//    @Override
//    public List<Tag> getWithFilters(Map<String, String> fields) throws DaoException {
//        return null;
//    }

    @Override
    public Tag getByName(String name) throws DaoException {
        try {
            List<Tag> list = jdbcTemplate.query(QUERY_GET_BY_NAME, tagMapper, name);
            if (list == null || list.isEmpty()) {
                throw new DaoException(NO_ENTITY_WITH_NAME);
            } else {
                return list.get(0);
            }

        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY_WITH_NAME);
        }
    }
}
