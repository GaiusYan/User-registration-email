package com.student.demo.appuser;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUSerService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final static String USER_NOT_FOUND = "user with email %s not found";
    @Override
    public UserDetails loadUserByUsername(String email) 
            throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
            .orElseThrow(() -> 
                new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public String signUpUser(AppUser appUser){
        boolean userExists = appUserRepository
            .findByEmail(appUser.getEmail())
            .isPresent();
        
        if (userExists) {
            throw new IllegalStateException("email already taken");
        }
        
        String encodedPassword = bCryptPasswordEncoder
            .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        //TODO: SEND confirmation token

        return "it works";
    }

}
