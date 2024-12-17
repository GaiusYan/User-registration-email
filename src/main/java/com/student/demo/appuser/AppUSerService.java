package com.student.demo.appuser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.student.demo.Registration.token.ConfirmationToken;
import com.student.demo.Registration.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUSerService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    private final static String USER_NOT_FOUND = "user with email %s not found";
    @Override
    public AppUser loadUserByUsername(String email) 
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

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = ConfirmationToken.builder()
            .token(token)
            .createdAt(LocalDateTime.now())
            .expiresAt(LocalDateTime.now().plusMinutes(15))
            .confirmAt(null)
            .appUser(appUser)
            .build();

        confirmationTokenService.saveConfirmationToken(
            confirmationToken
        );

        //TODO: send email
        return token;
    }

    public AppUser createAppUser(AppUser appUser){
        return appUserRepository.save(appUser);
    }

    public void enabledAppUser(String email){
        AppUser appUser = loadUserByUsername(email);
        appUser.setEnabled(true);
        createAppUser(appUser);
    }
}
