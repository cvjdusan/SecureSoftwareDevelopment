package com.zuehlke.securesoftwaredevelopment.controller;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import com.zuehlke.securesoftwaredevelopment.domain.HashedUser;
import com.zuehlke.securesoftwaredevelopment.domain.User;
import com.zuehlke.securesoftwaredevelopment.repository.HashedUserRepository;
import com.zuehlke.securesoftwaredevelopment.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordResetController {

    private final HashedUserRepository repository;
    private final UserRepository userRepository;

    PasswordResetController(HashedUserRepository repository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.repository = repository;
    }

    @PostMapping("/perform-password-change")
    public ResponseEntity<Void> resetPassword(String username, String newPassword) {
        userRepository.changePassword(username, newPassword);
        return ResponseEntity.noContent().build();
    }

}
