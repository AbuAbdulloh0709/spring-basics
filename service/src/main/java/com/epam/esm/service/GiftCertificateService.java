package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificate> {
    GiftCertificate update(GiftCertificate updateCertificate, long certificateId);

    List<GiftCertificate> doFilter(MultiValueMap<String, String> requestParams, int page, int size);

}
