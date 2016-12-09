package org.igoodwill.jtutorsb.configuration;

import javax.sql.DataSource;

import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JavaConfiguration {
	@Bean
	ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
		registrationBean.addUrlMappings("/console/*");
		return registrationBean;
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
}
