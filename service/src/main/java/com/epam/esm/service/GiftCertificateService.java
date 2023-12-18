package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;

public interface GiftCertificateService extends BaseService<GiftCertificate> {

    void update(GiftCertificate updateCertificate, long certificateId) throws DaoException, IncorrectParameterException;

//    List<GiftCertificate> searchAndGetByTagName(
//            String searchWord, String tagName, boolean doNameSort, boolean doDateSort, boolean isDescending);


}
