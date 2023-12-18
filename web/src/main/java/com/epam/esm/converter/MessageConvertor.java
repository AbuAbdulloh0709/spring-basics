package com.epam.esm.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageConvertor {
    private static ResourceBundleMessageSource messageSource;

    @Autowired
    MessageConvertor(ResourceBundleMessageSource messageSource) {
        MessageConvertor.messageSource = messageSource;
    }

    public static String toLocale(String msg) {
        return messageSource.getMessage(msg, null, LocaleContextHolder.getLocale());
    }
}
