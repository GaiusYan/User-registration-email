package com.student.demo.Registration;

import java.time.LocalDateTime;

import javax.management.relation.Role;

import org.springframework.stereotype.Service;

import com.student.demo.Registration.token.ConfirmationToken;
import com.student.demo.Registration.token.ConfirmationTokenService;
import com.student.demo.appuser.AppUSerService;
import com.student.demo.appuser.AppUser;
import com.student.demo.email.EmailSender;

import lombok.AllArgsConstructor;

@Service   
@AllArgsConstructor
public class RegistrationService {
    private final AppUSerService appUSerService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public String register(RegistrationRequest request){
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if(!isValidEmail){
            throw new IllegalStateException("Email not valid");
        }
        String token =  appUSerService.signUpUser(new AppUser(
            request.getFirstName(),
            request.getLastName(),
            request.getEmail(),
            request.getPassword(),
            com.student.demo.appuser.Role.USER
        ));
        String link = "http://localhost:8080/api/v1/registration/confirm?token="+ token;
        emailSender.send(
            request.getEmail(),
            token,link);
        return token;
    }

    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);

        if (confirmationToken.getConfirmAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiresAt = confirmationToken.getExpiresAt();

        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        appUSerService.enabledAppUser(confirmationToken.getAppUser().getEmail());
        return "Confirmed";
    }
}
