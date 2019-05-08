package ro.nextworks.workshop.tbs.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = "health.status=not_ok")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class HealthIndicatorUnHealthyTest {

  @Test
  void indicator_whenHealthySetup_returnsOk(@Autowired MockMvc mockMvc) throws Exception {
    mockMvc.perform(get("/management/health")).andExpect(status().isServiceUnavailable());
  }

}
