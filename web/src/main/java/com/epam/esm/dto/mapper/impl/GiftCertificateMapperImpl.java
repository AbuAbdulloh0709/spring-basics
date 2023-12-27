package com.epam.esm.dto.mapper.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.mapper.Mapper;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateMapperImpl implements Mapper<GiftCertificate, GiftCertificateDto> {
    @Override
    public GiftCertificate mapToEntity(GiftCertificateDto dto) {
        GiftCertificate giftCertificate = new GiftCertificate();

        giftCertificate.setId(dto.getId());
        giftCertificate.setName(dto.getName());
        giftCertificate.setDescription(dto.getDescription());
        giftCertificate.setPrice(dto.getPrice());
        giftCertificate.setDuration(dto.getDuration());
        giftCertificate.setCreateDate(dto.getCreateDate());
        giftCertificate.setLastUpdateDate(dto.getLastUpdateDate());
        giftCertificate.setTags(dto.getTags());

        return giftCertificate;
    }

    @Override
    public GiftCertificateDto mapToDto(GiftCertificate entity) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();

        giftCertificateDto.setId(entity.getId());
        giftCertificateDto.setName(entity.getName());
        giftCertificateDto.setDescription(entity.getDescription());
        giftCertificateDto.setPrice(entity.getPrice());
        giftCertificateDto.setDuration(entity.getDuration());
        giftCertificateDto.setCreateDate(entity.getCreateDate());
        giftCertificateDto.setLastUpdateDate(entity.getLastUpdateDate());
        giftCertificateDto.setTags(entity.getTags());

        return giftCertificateDto;
    }
}
