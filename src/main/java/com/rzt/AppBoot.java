package com.rzt;

import org.apache.log4j.Logger;
import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJSONDoc
@EnableAsync // there are some functions that need to happen in async. Example: Sending emails
@ComponentScan( "com.rzt" )
@EntityScan( "com.rzt" )
@EnableAutoConfiguration( exclude = {
		org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class } )
public class AppBoot {

	static Logger LOGGER = Logger.getLogger(AppBoot.class);

	public static void main( String[] args )
	{
		SpringApplication application = new SpringApplication(AppBoot.class);
		application.run(args);

		LOGGER.info("Application Started successfully from class: " + AppBoot.class.getSimpleName());

	}

}
