package com.rzt;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.apache.log4j.Logger;
import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import com.rzt.google.GoogleApiAuthConfig;
import com.rzt.session.AutherizedServiceFilter;
import com.rzt.session.GoogleOAuthFilter;

/**
 * Starting Point of the application from where the Spring application will be iniated
 */
@SpringBootApplication
@EnableJSONDoc
@EnableAsync // there are some functions that need to happen in async. Example: Sending emails
@ComponentScan( "com.rzt" )
@EntityScan( "com.rzt" )
@EnableAutoConfiguration( exclude = {
		org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class } )
public class AppBoot {

	static Logger logger = Logger.getLogger(AppBoot.class);

	@Autowired
	private Environment env;

	/**
	 * Main Entry point of the application
	 * 
	 * @param args
	 */
	public static void main( String[] args )
	{
		SpringApplication application = new SpringApplication(AppBoot.class);
		application.run(args);

		logger.info("Application Started successfully from class: " + AppBoot.class.getSimpleName());

	}

	/**
	 * This is the filter to handle the google's redirection where Google redirects a code to access
	 * user's profile after the OAuth2 authentication
	 *
	 * @return
	 */
	@Bean
	public FilterRegistrationBean googleLoginFilter()
	{
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new GoogleOAuthFilter());
		registration.setOrder(1);
		registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
		registration.addUrlPatterns("/googlelogin");
		return registration;
	}

	/**
	 * This contains all the API auth Configs to call the Google APIs.
	 *
	 * @return GoogleApiConfig
	 */
	@Bean
	public GoogleApiAuthConfig googleApiConfig()
	{
		return new GoogleApiAuthConfig(env);
	}


	/**
	 * This filter looks into each service that expects the user to be authenticated in order to
	 * process, and filter out with a valid response if the user is not logged in.
	 *
	 * @return
	 */
	@Bean
	public FilterRegistrationBean serviceRequestFilter()
	{
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new AutherizedServiceFilter());
		registration.setOrder(4);
		registration.addUrlPatterns("/*");
		return registration;
	}

}
