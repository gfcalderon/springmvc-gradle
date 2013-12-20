package com.springapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Main SpringApp controller
 * User: Gerardo Fernández
 * Date: 15/11/2013
 */
@Controller
public class SpringappController {

    @RequestMapping( { "", "/" } )
    public String loadHomePage( Model model ) {
        model.addAttribute( "name", "SpringApp" );
        return "home";
    }

}
