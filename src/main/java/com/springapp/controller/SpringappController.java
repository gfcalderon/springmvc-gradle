package com.springapp.controller;

import com.springapp.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @RequestMapping( value = "/login", method = { RequestMethod.GET, RequestMethod.POST } )
    public String login() {
        return "login";
    }

    @RequestMapping( value = { "", "/", "/home" }, method = RequestMethod.GET )
    public String welcome( Model model ) {
        model.addAttribute( "person", person );
        model.addAttribute( "newPerson", new Person() );

        return "home";
    }

    @RequestMapping( value = "/identify", method = RequestMethod.POST )
    public String configure(@ModelAttribute( "person" ) final Person formPerson, final BindingResult result) {
        if ( !result.hasErrors() ) {
            person.setName( formPerson.getName() );
        }

        return "redirect:/home";
    }

    @RequestMapping( value = "/404", method = RequestMethod.GET )
    public String pageNotFound() {
        return "error/404";
    }

}
