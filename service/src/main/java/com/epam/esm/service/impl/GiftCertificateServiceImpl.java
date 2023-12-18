package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.IdentifiableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDAO giftCertificateDAO;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
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
    GiftCertificateValidator.validateForUpdate(updateCertificate);
    giftCertificateDAO.update(updateCertificate);
    }
}
