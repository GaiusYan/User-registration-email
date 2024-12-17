package com.student.demo.Registration;

import java.util.function.Predicate;

import org.springframework.stereotype.Service;

@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String t) {
        //TODO: Regex validate email
        return true;    
    }

}
