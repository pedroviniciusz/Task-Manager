package com.example.user.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {@Server(description = "Default Server URL")})
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("User API")
                        .contact(new Contact().name("Pedro Vinícius").url("https://github.com/pedroviniciusz"))
                        .termsOfService("http://swagger.io/terms/")
                        .version("1.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));

    }

}
