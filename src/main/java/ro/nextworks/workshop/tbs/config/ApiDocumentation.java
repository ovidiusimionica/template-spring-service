package ro.nextworks.workshop.tbs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Initialization of Swagger API.
 */
@Configuration
@EnableSwagger2
public class ApiDocumentation extends WebMvcConfigurationSupport {

  /**
   * The package name where the Rest API is located.
   */
  private static final String DOC_PACKAGE = "ro.nextworks.workshop.tbs.sayhello";
  /**
   * The symbolic version of this Rest API.
   */
  static final String API_VERSION = "v1.0";
  /**
   * The license of this Rest API.
   */
  static final String LICENSE = "Apache License Version 2.0";
  /**
   * The license url for online viewing.
   */
  static final String LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";

  /**
   * This symbolic title of this Rest API.
   */
  static final String REST_API_TITLE = "Template Boot Service Rest API";

  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage(DOC_PACKAGE))
        .paths(PathSelectors.any())
        .build()
        .tags(new Tag("Health Service", "Sample Spring Boot Rest Endpoint that tells you Hello! :)"))
        .apiInfo(metaData());

  }

  private ApiInfo metaData() {
    return new ApiInfoBuilder()
        .title(REST_API_TITLE)
        .version(API_VERSION)
        .license(LICENSE)
        .licenseUrl(LICENSE_URL)
        .build();
  }

  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
}