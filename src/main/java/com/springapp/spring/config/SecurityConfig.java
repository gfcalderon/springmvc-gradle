package com.springapp.spring.config;

import com.springapp.authentication.SpringappAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security Config
 * User: Gerardo Fernández
 * Date: 24/11/2014
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/resources/**" ).permitAll()
                .antMatchers( "/login*" ).anonymous()
                .anyRequest().authenticated();

        http.formLogin()
                .successHandler( springappAuthenticationSuccessHandler() )
                .loginPage( "/login" )
                .failureUrl( "/login?error=true" )
                .permitAll();

        http.logout()
                .logoutUrl( "/logout" )
                .logoutRequestMatcher( new AntPathRequestMatcher( "/logout" ) )
                .logoutSuccessUrl( "/login" )
                .invalidateHttpSession( true )
                .permitAll();
    }

    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
       auth.inMemoryAuthentication().withUser( "gideon" ).password( "gideon" ).roles( "USER" );
        auth.inMemoryAuthentication().withUser( "admin" ).password( "admin" ).roles( "ADMIN" );
    }

    @Bean
    @Qualifier( "springappAuthenticationSuccessHandler" )
    public SpringappAuthenticationSuccessHandler springappAuthenticationSuccessHandler() {
        return new SpringappAuthenticationSuccessHandler( "/home" );
    }

}