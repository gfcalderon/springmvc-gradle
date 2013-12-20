package com.springapp.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Spring application config class
 * User: Gerardo Fernández
 * Date: 20/12/2013
 */
@Configuration
@ComponentScan( basePackages = { "com.springapp" } )
@EnableWebMvc
@Import( { ThymeleafConfig.class } )
public class ApplicationConfig {

}
