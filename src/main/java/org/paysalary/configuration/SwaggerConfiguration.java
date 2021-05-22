package org.paysalary.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import({SpringDataRestConfiguration.class})
public class SwaggerConfiguration {

    @Bean
    public Docket EmployeeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Employee")
                .select()
                .apis(RequestHandlerSelectors.basePackage(
                        "org.paysalary.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(
                        new ApiInfoBuilder()
                                .title("Salary Pay")
                                .description("Salary Pay REST API")
                                .version("0.0.1")
                                .license("© Md Erfan Ullah Bhuiyan")
                                .licenseUrl("http://erfanu25.github.io")
                                .contact(
                                        new Contact(
                                                "Md Erfan Ullah Bhuiyan",
                                                "http://erfanu25.github.io",
                                                "mderfan2@gmail.com"))
                                .build());
    }
}

