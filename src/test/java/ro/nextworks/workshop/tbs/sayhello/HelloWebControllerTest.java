package ro.nextworks.workshop.tbs.sayhello;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HelloWebController.class)
@TestPropertySource(
    properties = {
        "spring.cloud.zookeeper.enabled=false",
        "spring.cloud.marathon.enabled=false",
    }
)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
class HelloWebControllerTest {

  private static final String HTTPS_HOST = "https://somehost-int.rd.corpintra";
  private static final String HTTP_HOST = "http://somehost-int.rd.corpintra";
  private static final String HELLO_WORLD = "Hello World !";
  private static final String HELLO_JOHN_DOE = "Hello John Doe !";

  @Autowired
  private MockMvc mockMvc;

  @Test
  void main_whenRequestRootUri_returnRedirect() throws Exception {
    mockMvc.perform(get("/")).
        andExpect(status().is3xxRedirection());
  }

  @Test
  void main_whenRequestRootUri_returnHttpSchema() throws Exception {
    mockMvc.perform(get("/")).
        andExpect(redirectedUrl("http://localhost/swagger-ui.html"));
  }

  @Test
  void main_whenRequestRootWithHttpsSchema_returnSchemaOverwrittenByDefaultSettings() throws Exception {
    mockMvc.perform(get(HTTPS_HOST + ":443")).
        andExpect(redirectedUrl(HTTP_HOST + "/swagger-ui.html"));
  }

  @Test
  void testRest_SayHello_withEmptyArgument() throws Exception {
    MvcResult mvcResult = sayHello("");
    assertEquals(HELLO_WORLD, mvcResult.getResponse().getContentAsString());
  }

  @Test
  void testRest_SayHello_withEmptyArgumentSpaces() throws Exception {
    MvcResult mvcResult = sayHello("   ");
    assertEquals(HELLO_WORLD, mvcResult.getResponse().getContentAsString());
  }

  @Test
  void testRest_SayHello_withNullArgument() throws Exception {
    MvcResult mvcResult = sayHello(null);
    assertEquals(HELLO_WORLD, mvcResult.getResponse().getContentAsString());
  }

  @Test
  void testRest_SayHello_WithParam() throws Exception {
    MvcResult mvcResult = sayHello("John Doe");
    assertEquals(HELLO_JOHN_DOE, mvcResult.getResponse().getContentAsString());
  }

  private MvcResult sayHello(String name) throws Exception {
    return mockMvc.perform(name == null ? get("/hello") : get("/hello/" + name))
        .andExpect(status().is2xxSuccessful())
        .andReturn();
  }

}