package ro.nextworks.workshop.tbs.sayhello;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HelloWebController.class)
@TestPropertySource(
    properties = {
        "spring.cloud.zookeeper.enabled=false",
        "spring.cloud.marathon.enabled=false",
        "swagger.schema=https"
    }
)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
class HelloWebControllerWithHttpsSchemaTest {

  private static final String HTTPS_HOST = "https://somehost-int.rd.corpintra";
  @Autowired
  private MockMvc mockMvc;

  @Test
  void main_whenRequestRootUri_returnRedirect() throws Exception {
    mockMvc.perform(get("/")).
        andExpect(status().is3xxRedirection());
  }

  @Test
  void main_whenRequestRootUri_returnSchemaOverwrittenBySettings() throws Exception {
    mockMvc.perform(get("/")).
        andExpect(redirectedUrl("https://localhost/swagger-ui.html"));
  }

  @Test
  void main_whenRequestRootWithHttpsSchema_returnHttpsSchema() throws Exception {
    mockMvc.perform(get(HTTPS_HOST + ":443")).
        andExpect(redirectedUrl(HTTPS_HOST + "/swagger-ui.html"));
  }
}