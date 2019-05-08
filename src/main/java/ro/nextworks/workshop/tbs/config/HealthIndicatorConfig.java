package ro.nextworks.workshop.tbs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HealthIndicatorConfig {

  @Value("${health.status:ok}")
  private String healthStatus;

  @Bean
  public HealthyIndicator unHealthyIndicator() {
    return new HealthyIndicator(healthStatus);
  }


  public static class HealthyIndicator extends AbstractHealthIndicator {

    private final String indicatorStatus;

    public HealthyIndicator(String healthStatus) {
      this.indicatorStatus = healthStatus;
    }

    @Override
    protected void doHealthCheck(Builder builder) {
      if ("ok".equalsIgnoreCase(indicatorStatus)) {
        builder.up();
      } else {
        builder.down();
      }
    }
  }


}
