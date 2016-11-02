package com.springapp.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * Spring config class for Thymeleaf resolver
 * User: Gerardo Fernández
 * Date: 20/12/2013
 */
@Configuration
public class ThymeleafConfig {

    @Bean
    public ServletContextTemplateResolver templateResolver() {
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setPrefix( "/WEB-INF/view/" );
        resolver.setSuffix( ".html" );
        resolver.setTemplateMode( "HTML5" );
        resolver.setCacheable( true );
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver( templateResolver() );
        engine.addDialect( new SpringSecurityDialect() );
        return engine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine( templateEngine() );
        resolver.setCharacterEncoding( "UTF-8" );
        resolver.setOrder( 1 );
        return resolver;
    }

}