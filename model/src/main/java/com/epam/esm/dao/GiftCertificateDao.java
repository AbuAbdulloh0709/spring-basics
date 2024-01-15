package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {
    /**
     * Method for getting an entity object by name.
     *
     * @param name name of entity to get
     * @return optional Entity object
     */
    Optional<GiftCertificate> getByName(String name);

    /**
     * Method for updating an entity.
     *
     * @param giftCertificate Entity object
     * @return Entity object
     */
    GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Method for filtering Gift Certificates with multiple part of fields.
     *
     * @param fields part of entity fields to filter
     * @return List of Entities
     */
    List<GiftCertificate> doFilter(MultiValueMap<String, String> fields, Pageable pageable);

    /**
     * Method for checking Gift Certificates has the Tag entity by ID.
     *
     * @param tagId ID of Tag entity
     * @return boolean
     */
    boolean giftCertificatesHasTagByTagID(long tagId);


}
