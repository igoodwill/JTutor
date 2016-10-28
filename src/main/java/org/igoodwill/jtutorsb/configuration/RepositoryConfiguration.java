package org.igoodwill.jtutorsb.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = { "org.igoodwill.jtutorsb.repositories" })
@EnableTransactionManagement
public class RepositoryConfiguration {
}
