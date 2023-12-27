package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.*;
import com.epam.esm.handler.DateHandler;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.IdentifiableValidator;
import com.epam.esm.validator.TagValidator;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.esm.dao.creator.FilterParameter.*;
import static com.epam.esm.exceptions.ExceptionMessageKey.GIFT_CERTIFICATE_NOT_FOUND;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final DateHandler dateHandler;

    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao, DateHandler dateHandler) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.dateHandler = dateHandler;
    }

    @Override
    @Transactional
    public GiftCertificate insert(GiftCertificate giftCertificate) {
        ExceptionResult exceptionResult = new ExceptionResult();
        GiftCertificateValidator.validate(giftCertificate, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        String giftCertificateName = giftCertificate.getName();
        boolean isGiftCertificateExist = giftCertificateDao.getByName(giftCertificateName).isPresent();
        if (isGiftCertificateExist) {
            throw new DuplicateEntityException(ExceptionMessageKey.GIFT_CERTIFICATE_EXIST);
        }
        giftCertificate.setCreateDate(dateHandler.getCurrentDate());
        giftCertificate.setLastUpdateDate(dateHandler.getCurrentDate());
        removeDuplicateTags(giftCertificate);
        giftCertificate.setTags(updateListFromDatabase(giftCertificate.getTags()));
        return giftCertificateDao.insert(giftCertificate);
    }

    @Override
    public GiftCertificate getById(long id) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        Optional<GiftCertificate> optionalTag = giftCertificateDao.getById(id);
        if (optionalTag.isEmpty()) {
            throw new NoSuchEntityException(GIFT_CERTIFICATE_NOT_FOUND);
        }

        return optionalTag.get();
    }

    @Override
    public List<GiftCertificate> getAll(int page, int size) {
        Pageable pageable = createPageRequest(page, size);
        return giftCertificateDao.getAll(pageable);
    }

    @Override
    public List<GiftCertificate> doFilter(MultiValueMap<String, String> requestParams, int page, int size) {
        ExceptionResult exceptionResult = new ExceptionResult();
        String name = getSingleRequestParameter(requestParams, NAME);
        if (name != null) {
            GiftCertificateValidator.validateName(name, exceptionResult);
        }
        List<String> tagNames = requestParams.get(TAG_NAME);
        if (tagNames != null) {
            for (String tagName : tagNames) {
                TagValidator.validateName(tagName, exceptionResult);
            }
        }
        String sortNameType = getSingleRequestParameter(requestParams, DATE_SORT);
        if (sortNameType != null) {
            IdentifiableValidator.validateSortType(sortNameType.toUpperCase(), exceptionResult);
        }
        String sortCreateDateType = getSingleRequestParameter(requestParams, NAME_SORT);
        if (sortCreateDateType != null) {
            IdentifiableValidator.validateSortType(sortCreateDateType.toUpperCase(), exceptionResult);
        }
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        Pageable pageRequest = createPageRequest(page, size);
        return giftCertificateDao.doFilter(requestParams, pageRequest);
    }

    @Override
    public void removeById(long id) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.getById(id);
        if (giftCertificate.isEmpty()) {
            throw new NoSuchEntityException(GIFT_CERTIFICATE_NOT_FOUND);
        }
        giftCertificateDao.removeById(id);
    }

    @Override
    public GiftCertificate update(GiftCertificate updateCertificate, long certificateId) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(certificateId, exceptionResult);
        GiftCertificateValidator.validateForUpdate(updateCertificate, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        Optional<GiftCertificate> oldGiftCertificate = giftCertificateDao.getById(certificateId);
        if (oldGiftCertificate.isEmpty()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }
        String giftCertificateName = updateCertificate.getName();
        boolean isGiftCertificateExist = giftCertificateDao.getByName(giftCertificateName).isPresent();
        if (isGiftCertificateExist && !oldGiftCertificate.get().getName().equals(giftCertificateName)) {
            throw new DuplicateEntityException(ExceptionMessageKey.GIFT_CERTIFICATE_EXIST);
        }

        removeDuplicateTags(updateCertificate);
        updateCertificate.setTags(updateListFromDatabase(updateCertificate.getTags()));
        GiftCertificate newGiftCertificate = updateObject(updateCertificate, oldGiftCertificate.get());
        return giftCertificateDao.update(newGiftCertificate);
    }

    private void removeDuplicateTags(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
        if (tags != null) {
            List<Tag> result = new ArrayList<>();
            for (Tag tag : tags) {
                if (!result.contains(tag)) {
                    result.add(tag);
                }
            }
            giftCertificate.setTags(result);
        }
    }

    private List<Tag> updateListFromDatabase(List<Tag> newListOfTags) {
        List<Tag> tagsToPersist = new ArrayList<>();
        if (newListOfTags != null) {
            for (Tag tag : newListOfTags) {
                Optional<Tag> tagOptional = tagDao.getByName(tag.getName());
                if (tagOptional.isPresent()) {
                    tagsToPersist.add(tagOptional.get());
                } else {
                    tagsToPersist.add(tag);
                }
            }
        }
        return tagsToPersist;
    }

    public GiftCertificate updateObject(GiftCertificate newGiftCertificate, GiftCertificate oldGiftCertificate) {
        String name = newGiftCertificate.getName();
        if (!Objects.isNull(name)) {
            oldGiftCertificate.setName(name);
        }

        String description = newGiftCertificate.getDescription();
        if (!Objects.isNull(description)) {
            oldGiftCertificate.setDescription(description);
        }

        BigDecimal price = newGiftCertificate.getPrice();
        if (!Objects.isNull(price)) {
            oldGiftCertificate.setPrice(price);
        }

        int duration = newGiftCertificate.getDuration();
        if (duration != 0) {
            oldGiftCertificate.setDuration(duration);
        }

        List<Tag> tags = newGiftCertificate.getTags();
        if (!Objects.isNull(tags)) {
            oldGiftCertificate.setTags(tags);
        }

//        oldGiftCertificate.setLastUpdateDate(dateHandler.getCurrentDate());
        return oldGiftCertificate;
    }


}
