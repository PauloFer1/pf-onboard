package com.pfernand.pfonboard.pfonboard.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.inject.Inject;

@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private final Boolean swaggerDocsEnabled;

    @Inject
    public SwaggerConfiguration(@Value("${swagger.enabled}") Boolean swaggerDocsEnabled) {
        this.swaggerDocsEnabled = swaggerDocsEnabled;
    }


    @Bean
    public Docket newsApi() {
        log.debug("SWAGGER CONFIG enabled: {}", swaggerDocsEnabled);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(swaggerDocsEnabled)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("pf-onboard")
                .description("API Doc for pf-onboard")
                .version("1.0")
                .build();
    }
}
