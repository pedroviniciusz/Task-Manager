package com.example.gateway.core.messages;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.List;

@Configuration
public class Messages {

    public static final String AUTH_HEADER_IS_INVALID = "auth.header.invalid";
    public static final String AUTH_HEADER_HAS_EXPIRED = "auth.header.expired";
    public static final String MISSING_AUTH_HEADER = "auth.header.missing";

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setPropertiesPersister(new YamlPropertiesLoader());
        messageSource.setFileExtensions(List.of(".yml", ".yaml"));
        return messageSource;
    }

    public String getMessage(String code) {
        return messageSource().getMessage(code, null, LocaleContextHolder.getLocale());
    }

}
