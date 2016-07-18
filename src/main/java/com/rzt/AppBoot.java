package com.rzt;

import java.util.EnumSet;
import java.util.Properties;
import javax.servlet.DispatcherType;
import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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

    static final Logger logger = LoggerFactory.getLogger(AppBoot.class);

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

    /**
     * this is the email sender bean. the properties needed to initialize is placed in the
     * application.properties
     *
     * This bean is injected in EmailServiceImpl
     *
     * @return returns a JavaMailSender bean
     */
    @Bean
    JavaMailSenderImpl mailSender()
    {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setPort(Integer.parseInt(env.getProperty("javamail.port")));
        mailSenderImpl.setHost(env.getProperty("javamail.host"));
        mailSenderImpl.setUsername(env.getProperty("javamail.username"));
        mailSenderImpl.setPassword(env.getProperty("javamail.password"));
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.transport.protocol", env.getProperty("javamail.protocol"));
        javaMailProperties.put("mail.smtp.auth", env.getProperty("javamail.auth"));
        javaMailProperties.put("mail.smtp.starttls.enable", env.getProperty("javamail.starttls.enable"));
        javaMailProperties.put("mail.debug", env.getProperty("javamail.mail.debug"));

        mailSenderImpl.setJavaMailProperties(javaMailProperties);

        return mailSenderImpl;
    }

}
