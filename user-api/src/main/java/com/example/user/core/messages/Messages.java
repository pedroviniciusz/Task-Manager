package com.example.user.core.messages;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.List;

@Configuration
public class Messages {

    public static final String USER_NOT_FOUND = "user.not.found";
    public static final String SUBJECT = "email.subject";
    public static final String BODY = "email.body";
    public static final String INVALID_CPF = "invalid.cpf";
    public static final String PASSWORD_CANT_BE_EMPTY = "password.cant.be.empty";
    public static final String ALREADY_EXISTS_USER_BY_THIS_CPF = "already.exists.user.cpf";
    public static final String ALREADY_EXISTS_USER_BY_THIS_USERNAME = "already.exists.user.username";
    public static final String ALREADY_EXISTS_USER_BY_THIS_EMAIL = "already.exists.user.email";

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

    public String getMessage(String code, String... args) {
        return messageSource().getMessage(code, args, LocaleContextHolder.getLocale());
    }

}
