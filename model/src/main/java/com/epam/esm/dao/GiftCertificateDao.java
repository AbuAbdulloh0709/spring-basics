package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {

    Optional<GiftCertificate> getByName(String name);

    GiftCertificate update(GiftCertificate giftCertificate);

    List<GiftCertificate> doFilter(MultiValueMap<String, String> fields, Pageable pageable);


}
