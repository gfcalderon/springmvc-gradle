package com.springapp.spring.config.core;

import com.springapp.spring.config.ApplicationConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

/**
 * Main Spring Initializer
 * User: Gerardo Fernández
 * Date: 24/11/2014
 */
public class SpringMVCInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup( ServletContext servletContext ) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register( ApplicationConfig.class );
        context.setServletContext( servletContext );

        // Spring MVC front controller
        Dynamic servlet = servletContext.addServlet( "dispatcher", new DispatcherServlet( context ) );
        servlet.addMapping( "/" );
        servlet.setLoadOnStartup( 1 );
    }

}