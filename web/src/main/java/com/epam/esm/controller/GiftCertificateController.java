package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.mapper.impl.GiftCertificateMapperImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.hateoas.impl.GiftCertificateHateoasAdder;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gift_certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateMapperImpl mapper;
    private final GiftCertificateHateoasAdder hateoasAdder;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, GiftCertificateMapperImpl giftCertificateMapper, GiftCertificateHateoasAdder hateoasAdder) {
        this.giftCertificateService = giftCertificateService;
        this.mapper = giftCertificateMapper;
        this.hateoasAdder = hateoasAdder;
    }

    @GetMapping("/{id}")
    public GiftCertificateDto certificateById(@PathVariable long id) {
        GiftCertificate giftCertificate = giftCertificateService.getById(id);

        GiftCertificateDto giftCertificateDto = mapper.mapToDto(giftCertificate);
        hateoasAdder.addLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    @GetMapping
    public List<GiftCertificateDto> allCertificates(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<GiftCertificate> certificates = giftCertificateService.getAll(page, size);
        return certificates.stream()
                .map(mapper::mapToDto)
                .peek(hateoasAdder::addLinks)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto createGiftCertificate(@RequestBody GiftCertificateDto giftCertificate) {
        GiftCertificate addedGiftCertificate = giftCertificateService.insert(
                mapper.mapToEntity(giftCertificate));

        GiftCertificateDto giftCertificateDto = mapper.mapToDto(addedGiftCertificate);
        hateoasAdder.addLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCertificateById(@PathVariable long id) {
        giftCertificateService.removeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateCertificate(@PathVariable long id,
                                                @RequestBody GiftCertificateDto giftCertificate) {
        GiftCertificate updatedCertificate = giftCertificateService.update(mapper.mapToEntity(giftCertificate), id);
        GiftCertificateDto giftCertificateDto = mapper.mapToDto(updatedCertificate);
        hateoasAdder.addLinks(giftCertificateDto);
        return giftCertificateDto;
    }


    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> doFilter(
            @RequestParam MultiValueMap<String, String> requestParams,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "5", required = false) int size) {

        List<GiftCertificate> giftCertificates = giftCertificateService.doFilter(requestParams, page, size);

        return giftCertificates.stream()
                .map(mapper::mapToDto)
                .peek(hateoasAdder::addLinks)
                .collect(Collectors.toList());
    }

}
