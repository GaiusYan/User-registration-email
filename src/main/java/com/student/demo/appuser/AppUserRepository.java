package com.student.demo.appuser;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AppUserRepository {
    Optional<AppUser> findByEmail(String email);
}
