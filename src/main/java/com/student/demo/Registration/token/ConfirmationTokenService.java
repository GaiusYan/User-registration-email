package com.student.demo.Registration.token;

import org.springframework.stereotype.Service;

import com.student.demo.appuser.AppUSerService;
import com.student.demo.appuser.AppUser;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken getToken(String token){
        return confirmationTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                    new IllegalStateException("Token not found"));
    }

    
}
