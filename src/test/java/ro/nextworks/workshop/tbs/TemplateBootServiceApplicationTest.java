package ro.nextworks.workshop.tbs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TemplateBootServiceApplication.class)
@TestPropertySource(
    properties = {
        "spring.cloud.zookeeper.enabled=false",
        "spring.cloud.marathon.enabled=false"
    }
)
class TemplateBootServiceApplicationTest {

  @Test
  void smokeTest() {
    // smoke test that the context is loading
  }
}
