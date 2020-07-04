package com.example.diseasereport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zhengweijun
 * Created on 2020-06-14
 */

@Profile({"local", "test", "development"})
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("activity-manage")
                .apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.example.activitymanage"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger Interface")
                .description("前后端数据交换格式：JSON")
                .license("CVTE实训小组")
                .version("v1.0.0")
                .build();
    }
}
