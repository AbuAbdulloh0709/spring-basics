package com.epam.esm.controller;

import com.epam.esm.dto.FilterRequest;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gift_certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @RequestMapping
    public List<GiftCertificate> getAll() throws IncorrectParameterException, DaoException {
        return giftCertificateService.getAll();
    }

    @RequestMapping("/{id}")
    public GiftCertificate getById(@PathVariable("id") long id) throws IncorrectParameterException, DaoException {
        return giftCertificateService.getById(id);
    }

    @PostMapping
    public ResponseEntity<String> createCertificate(@RequestBody GiftCertificate certificate) throws DaoException, IncorrectParameterException {
        giftCertificateService.insert(certificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateCertificate(@PathVariable long id,
                                                    @RequestBody GiftCertificate certificate) throws DaoException, IncorrectParameterException {
        giftCertificateService.update(certificate,id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @PostMapping("/filter")
    public List<GiftCertificate> filter(@RequestBody FilterRequest filterRequest) throws IncorrectParameterException, DaoException {
        return giftCertificateService.doFilter(filterRequest);
    }

}
