package com.epam.esm.dao;

import com.epam.esm.dto.FilterRequest;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DaoException;

import java.util.List;

public interface GiftCertificateDAO extends BaseDAO<GiftCertificate> {

    void update(GiftCertificate giftCertificate) throws DaoException;

    List<GiftCertificate> doFilter(FilterRequest filterRequest) throws DaoException;


}
