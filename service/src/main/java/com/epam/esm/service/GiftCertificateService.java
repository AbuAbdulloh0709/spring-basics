package com.epam.esm.service;

import com.epam.esm.dto.FilterRequest;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificate> {

    void update(GiftCertificate updateCertificate, long certificateId) throws DaoException, IncorrectParameterException;

    List<GiftCertificate> doFilter(FilterRequest filterRequest) throws DaoException;


}
