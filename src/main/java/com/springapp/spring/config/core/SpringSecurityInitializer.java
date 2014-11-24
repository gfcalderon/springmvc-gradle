package com.springapp.spring.config.core;

import com.springapp.spring.config.SecurityConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * WAR Spring Security Initializer
 * User: Gerardo Fernández
 * Date: 24/11/2014
 */
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public SpringSecurityInitializer() {
        super( SecurityConfig.class );
    }

}
