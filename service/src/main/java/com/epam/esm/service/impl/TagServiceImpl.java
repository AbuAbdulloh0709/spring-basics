package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.*;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.IdentifiableValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionMessageKey.TAG_NOT_FOUND;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag insert(Tag tag) {
        ExceptionResult exceptionResult = new ExceptionResult();
        TagValidator.validate(tag, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        String tagName = tag.getName();
        boolean isTagExist = tagDao.getByName(tagName).isPresent();
        if (isTagExist) {
            throw new DuplicateEntityException(ExceptionMessageKey.TAG_EXIST);
        }
        return tagDao.insert(tag);
    }

    @Override
    public Tag getById(long id) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        Optional<Tag> optionalTag = tagDao.getById(id);
        if (optionalTag.isEmpty()) {
            throw new NoSuchEntityException(TAG_NOT_FOUND);
        }

        return optionalTag.get();
    }

    @Override
    public List<Tag> getAll(int page, int size) {
        Pageable pageable = createPageRequest(page, size);
        return tagDao.getAll(pageable);
    }

    @Override
    public void removeById(long id) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        Optional<Tag> optionalTag = tagDao.getById(id);
        if (optionalTag.isEmpty()) {
            throw new NoSuchEntityException(TAG_NOT_FOUND);
        }
        tagDao.removeById(id);
    }

    @Override
    public Tag getMostPopularTagOfUserWithHighestCostOfAllOrders() {
        Optional<Tag> optionalTag = tagDao.getMostPopularTagWithHighestCostOfAllOrders();
        if (optionalTag.isEmpty()) {
            throw new NoSuchEntityException(TAG_NOT_FOUND);
        }
        return optionalTag.get();
    }
}
