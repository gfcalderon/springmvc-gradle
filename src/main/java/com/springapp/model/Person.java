package com.springapp.model;

import org.springframework.stereotype.Service;

/**
 * User: Gerardo Fernández
 * Date: 21/12/2013
 */
@Service
public class Person {

    private String name;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

}
