package com.example.gateway.core.messages;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.util.PropertiesPersister;

import java.io.*;
import java.util.Properties;

public class YamlPropertiesLoader implements PropertiesPersister {

    @Override
    public void load(Properties props, InputStream is) {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new InputStreamResource(is));
        props.putAll(yaml.getObject());
    }

    @Override
    public void load(Properties props, Reader reader) throws IOException {

    }

    @Override
    public void store(Properties props, OutputStream os, String header) throws IOException {

    }

    @Override
    public void store(Properties props, Writer writer, String header) throws IOException {

    }

    @Override
    public void loadFromXml(Properties props, InputStream is) throws IOException {

    }

    @Override
    public void storeToXml(Properties props, OutputStream os, String header) throws IOException {

    }

    @Override
    public void storeToXml(Properties props, OutputStream os, String header, String encoding) throws IOException {

    }

}
