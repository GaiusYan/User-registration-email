package com.student.demo.Registration;

import javax.management.relation.Role;

import org.springframework.stereotype.Service;

import com.student.demo.appuser.AppUSerService;
import com.student.demo.appuser.AppUser;

import lombok.AllArgsConstructor;

@Service   
@AllArgsConstructor
public class RegistrationService {
    private final AppUSerService appUSerService;
    private final EmailValidator emailValidator;
    public String register(RegistrationRequest request){
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if(!isValidEmail){
            throw new IllegalStateException("Email not valid");
        }
        return appUSerService.signUpUser(new AppUser(
            request.getFirstName(),
            request.getLastName(),
            request.getEmail(),
            request.getPassword(),
            com.student.demo.appuser.Role.USER
        ));
    }
}
