package br.com.mercadolivre.mutantidentifier.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@PropertySource(value = "classpath:app-docs.properties")
public class SwaggerConfig {

    @Value("${appinfo.desc}")
    private String appinfoDesc;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.mercadolivre"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "DNA analysis - API",
                appinfoDesc,
                "1.0",
                "Copyrights to MercadoLivre",
                new Contact("Ricardo Stephan (Carvo)", "", "ccarvo@gmail.com"),
                "", "", Collections.emptyList());
    }


}