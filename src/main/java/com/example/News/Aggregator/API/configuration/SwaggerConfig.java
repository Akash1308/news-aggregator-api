package com.example.News.Aggregator.API.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomConfig(){

        return new OpenAPI()
                .info(
                        new Info().title("News Aggregator API")
                                .description("By Akash")
                )
                .servers(Arrays.asList(new Server().url("http://localhost:8080").description("local"),
                                        new Server().url("http://localhost:8081").description("Mock")))
                .tags(Arrays.asList(
                        new Tag().name("Admin APIs"),
                        new Tag().name("API Health Check"),
                        new Tag().name("News API"),
                        new Tag().name("User APIs")
                        ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes(
                        "bearerAuth",new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                ));
    }
}
