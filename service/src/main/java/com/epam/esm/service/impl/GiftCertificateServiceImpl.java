package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.handler.DateHandler;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.IdentifiableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDAO giftCertificateDAO;
    private final TagDAO tagDAO;
    private final DateHandler dateHandler;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO, DateHandler dateHandler) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
        this.dateHandler = dateHandler;
    }

    @Override
    public void insert(GiftCertificate giftCertificate) throws DaoException, IncorrectParameterException {
        GiftCertificateValidator.validate(giftCertificate);
        giftCertificateDAO.insert(giftCertificate);
    }

    @Override
    public GiftCertificate getById(long id) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        return giftCertificateDAO.getById(id);
    }

    @Override
    public List<GiftCertificate> getAll() throws DaoException {
        return giftCertificateDAO.getAll();
    }

    @Override
    public void removeById(long id) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        giftCertificateDAO.removeById(id);
    }

    @Override
    public void update(GiftCertificate updateCertificate, long certificateId) throws DaoException, IncorrectParameterException {
        updateCertificate.setLastUpdateDate(dateHandler.getCurrentDate());
        GiftCertificateValidator.validateForUpdate(updateCertificate);

        updateCertificate.setId(certificateId);
        saveNewTags(updateCertificate);

        giftCertificateDAO.update(updateCertificate);
    }
    private void saveNewTags(GiftCertificate item) throws DaoException {
        List<Tag> allTags = tagDAO.getAll();
        List<Tag> requestTags = item.getTags();
        if (requestTags == null || requestTags.size() == 0) {
            return;
        }

        for (Tag requestTag : requestTags) {
            boolean isExist = false;
            for (Tag tag : allTags) {
                if (requestTag.getName().equalsIgnoreCase(tag.getName())) {
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                tagDAO.insert(requestTag);
            }
        }
    }

}
