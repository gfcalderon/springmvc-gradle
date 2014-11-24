package com.springapp.spring.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import java.util.Locale;

/**
 * Spring application config class
 * User: Gerardo Fernández
 * Date: 20/12/2013
 */
@Configuration
@ComponentScan( basePackages = { "com.springapp" } )
@EnableWebMvc
@Import( { ThymeleafConfig.class } )
public class ApplicationConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers( ResourceHandlerRegistry registry ) {
        registry.addResourceHandler( "/resources/**" ).addResourceLocations( "/resources/" );
    }

    @Override
    public void addInterceptors( InterceptorRegistry registry ) {
        registry.addInterceptor( localeChangeInterceptor() );
        registry.addInterceptor( webContentInterceptor() );
    }

    @Bean
    @Qualifier( "webContentInterceptor" )
    public WebContentInterceptor webContentInterceptor() {
        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        webContentInterceptor.setCacheSeconds( 0 );
        webContentInterceptor.setUseExpiresHeader( true );
        webContentInterceptor.setUseCacheControlHeader( true );
        webContentInterceptor.setUseCacheControlNoStore( true );
        return webContentInterceptor;
    }

    @Bean
    @Qualifier( "localeResolver" )
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale( new Locale( "es" ) );
        return localeResolver;
    }

    @Bean
    @Qualifier( "localeChangeInterceptor" )
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName( "lang" );
        return localeChangeInterceptor;
    }

    @Bean
    @Qualifier( "messageSource" )
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename( "/WEB-INF/i18n/springappTexts" );
        messageSource.setDefaultEncoding( "UTF-8" );
        messageSource.setCacheSeconds( 1 );
        return messageSource;
    }

}
