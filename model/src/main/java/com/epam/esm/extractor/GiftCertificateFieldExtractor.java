package com.epam.esm.extractor;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GiftCertificateFieldExtractor {

    public Map<String, String> extractData(GiftCertificate certificate) {
        Map<String, String> fields = new HashMap<>();

        if (certificate.getId() != 0) {
            fields.put("id", String.valueOf(certificate.getId()));
        }

        if (certificate.getName() != null && !certificate.getName().isEmpty()) {
            fields.put("name", certificate.getName());
        }

        if (certificate.getDescription() != null && !certificate.getDescription().isEmpty()) {
            fields.put("description", certificate.getDescription());
        }

        if (certificate.getPrice() != null) {
            fields.put("price", certificate.getPrice().toString());
        }

        if (certificate.getDuration() != 0) {
            fields.put("duration", String.valueOf(certificate.getDuration()));
        }

        return fields;
    }
}
