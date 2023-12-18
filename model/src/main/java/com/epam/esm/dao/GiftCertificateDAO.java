package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DaoException;

public interface GiftCertificateDAO extends BaseDAO<GiftCertificate> {

    void update(GiftCertificate giftCertificate) throws DaoException;

//    List<GiftCertificate> searchAndGetByTagName(
//            String searchWord, String tagName, boolean doNameSort, boolean doDateSort, boolean isDescending);


}
