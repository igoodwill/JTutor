package org.igoodwill.jtutorsb;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class JTutorSbApplication extends SpringBootServletInitializer {

	public static Logger log = Logger.getLogger(JTutorSbApplication.class.getName());

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(JTutorSbApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(JTutorSbApplication.class, args);
	}
}
