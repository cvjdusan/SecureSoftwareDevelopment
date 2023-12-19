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

    private final UserRepository userRepository;

    PasswordResetController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/perform-password-change")
    public ResponseEntity<Void> resetPassword(String username, String newPassword) {
        User user = userRepository.findUser(username);

        if(user == null) {
            return ResponseEntity.noContent().build();
        }

        if(!user.getBlocked()) {
            String resetLink = "https://example.com/";

            String emailBody = "Click on the link to change password " + resetLink;
            sendEmail(emailBody);

            int passwordChangeAttempts = user.getPasswordChangeAttempts() + 1;

            userRepository.incPasswordChangeAttempts(username, passwordChangeAttempts);

            if(passwordChangeAttempts >= 3) {
                userRepository.blockUser(username);
            }

        } else {
            System.out.println("User is blocked");
        }
        return ResponseEntity.noContent().build();
    }

    private void sendEmail(String emailBody) {
        System.out.println("Sending: " + emailBody);
    }

}
