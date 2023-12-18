package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.IdentifiableValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public void insert(Tag tag) throws DaoException, IncorrectParameterException {
        TagValidator.validateName(tag.getName());
        tagDAO.insert(tag);
    }

    @Override
    public Tag getById(long id) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        return tagDAO.getById(id);
    }

    @Override
    public List<Tag> getAll() throws DaoException, IncorrectParameterException {
        return tagDAO.getAll();
    }

    @Override
    public void removeById(long id) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        tagDAO.removeById(id);
    }

    @Override
    public Tag getByName(String name) throws DaoException, IncorrectParameterException {
        TagValidator.validateName(name);
        return tagDAO.getByName(name);
    }
}
