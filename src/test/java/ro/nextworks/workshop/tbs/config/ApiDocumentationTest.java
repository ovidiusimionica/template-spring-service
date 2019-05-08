package ro.nextworks.workshop.tbs.config;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@EnableSwagger2
@ContextConfiguration(classes = ApiDocumentation.class)
@TestPropertySource(
    properties = {
        "spring.cloud.zookeeper.enabled=false",
        "spring.cloud.marathon.enabled=false"
    }
)
class ApiDocumentationTest {

  @Autowired
  private MockMvc mockMvc;


  @Test
  void testSwagger_APIDocumented() throws Exception {
    mockMvc.perform(get("/v2/api-docs"))
        .andExpect(jsonPath("$.info.version").value(ApiDocumentation.API_VERSION))
        .andExpect(jsonPath("$.info.title").value(ApiDocumentation.REST_API_TITLE))
        .andExpect(jsonPath("$.info.license.name").value(ApiDocumentation.LICENSE))
        .andExpect(jsonPath("$.info.license.url").value(ApiDocumentation.LICENSE_URL))
    ;
  }

}
