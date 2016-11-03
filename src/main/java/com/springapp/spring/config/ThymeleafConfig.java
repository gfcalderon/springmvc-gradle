package com.springapp.spring.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Spring config class for Thymeleaf resolver
 * User: Gerardo Fernández
 * Date: 20/12/2013
 */
@Configuration
public class ThymeleafConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void setApplicationContext( ApplicationContext applicationContext ) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext( applicationContext );
        resolver.setPrefix( "/WEB-INF/view/" );
        resolver.setSuffix( ".html" );
        resolver.setTemplateMode(TemplateMode.HTML );
        resolver.setCacheable( true );
        return resolver;
    }

    @Bean
    public TemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler( true );
        engine.setTemplateResolver( templateResolver() );
        engine.addDialect( new SpringSecurityDialect() );
        return engine;
    }

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine( templateEngine() );
        resolver.setCharacterEncoding( "UTF-8" );
        resolver.setOrder( 1 );
        return resolver;
    }

}