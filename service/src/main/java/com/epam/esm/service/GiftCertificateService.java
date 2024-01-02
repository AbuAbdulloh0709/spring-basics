package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificate> {
    /**
     * Method for updating an entity object by ID.
     *
     * @param updateCertificate entity to update
     * @param certificateId ID of the entity
     * @return Entity object
     */
    GiftCertificate update(GiftCertificate updateCertificate, long certificateId);

    /**
     * Method for filtering gift certificates by requestParams.
     *
     * @param requestParams request parameters for filtering
     * @param page the number of page
     * @param size the size of each page
     * @return List of GiftCertificates
     */
    List<GiftCertificate> doFilter(MultiValueMap<String, String> requestParams, int page, int size);

}
