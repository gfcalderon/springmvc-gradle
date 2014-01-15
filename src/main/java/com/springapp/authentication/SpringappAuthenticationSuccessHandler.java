package com.springapp.authentication;


import com.springapp.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Gerardo Fernández
 * Date: 15/01/2014
 */
public class SpringappAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private Person person;

    public SpringappAuthenticationSuccessHandler( String defaultTargetUrl ) {
        super( defaultTargetUrl );
    }

    @Override
    public void onAuthenticationSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication ) throws IOException, ServletException {
        person.setName( authentication.getName().equals( "admin" ) ? "Administrador" : authentication.getName() );

        super.onAuthenticationSuccess( request, response, authentication );
    }
}
