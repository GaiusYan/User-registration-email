package com.student.demo.Registration.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ConfirmationTokenRepository 
    extends JpaRepository<ConfirmationToken,Long>{

    Optional<ConfirmationToken> findByToken(String token);
}
