package com.ms.jpa.delete.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * The database configuration which enables spring's jpa-auditing for automatically
 * timestamp injection.
 *
 * @author MS
 */
@Configuration
@EnableJpaAuditing
public class DatabaseConfiguration {

  /**
   * @return a simple username as auditor
   */
  @Bean
  public AuditorAware<String> auditorAware() {
    return () -> "Administrator";
  }

}
