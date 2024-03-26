package com.example.api.auth.core.messages;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.List;

@Configuration
public class Messages {

    public static final String BAD_CREDENTIALS = "bad.credentials";

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
