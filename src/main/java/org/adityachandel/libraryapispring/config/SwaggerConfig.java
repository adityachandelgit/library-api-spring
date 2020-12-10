package org.adityachandel.libraryapispring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .useDefaultResponseMessages(false)
      .select()
      .apis(RequestHandlerSelectors.basePackage("org.adityachandel.libraryapispring.controller"))
      .paths(PathSelectors.any())
      .build()
      .apiInfo(apiInfo());
  }

  @Bean
  UiConfiguration uiConfig() {
    return UiConfigurationBuilder.builder()
            .docExpansion(DocExpansion.LIST)
            .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("Library API")
      .description("Library API is used for managing the books in the library. " +
              "All API endpoints require a valid Basic token in the 'Authorization' header.")
      .version("1.0.0")
      .build();
  }

}
