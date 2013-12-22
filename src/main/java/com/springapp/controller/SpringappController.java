package com.springapp.controller;

import com.springapp.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Main SpringApp controller
 * User: Gerardo Fernández
 * Date: 15/11/2013
 */
@Controller
public class SpringappController {

    @Autowired
    private Person person;

    @RequestMapping( value = "/", method = RequestMethod.GET )
    public String start( Model model ) {
        if ( person.getName() == null || person.getName().isEmpty() ) {
            person.setName( "Springapp" );
        }

        return "redirect:/home";
    }

    @RequestMapping( value = "/home" )
    public String welcome( Model model ) {
        model.addAttribute( "name", this.person.getName() );
        model.addAttribute( "person", new Person() );

        return "home";
    }

    @RequestMapping( value = "/identify", method = RequestMethod.POST )
    public String configure( final Person person, final ModelMap model, final BindingResult result ) {
        if ( !result.hasErrors() ) {
            this.person.setName( person.getName() );
        }

        model.clear();

        return "redirect:/home";
    }

}
