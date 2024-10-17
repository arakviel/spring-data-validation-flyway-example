package com.arakviel.eventlistenerdemo.listener;

import com.arakviel.eventlistenerdemo.event.CreatedUserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WelcomeUserListener {

    private final JavaMailSender mailSender;

    @EventListener
    public void handleUserCreated(CreatedUserEvent event) {
        String email = event.getUser().getEmail();
        //sendWelcomeEmail(email);
        System.out.println("Welcome email sent to " + email);
    }

    private void sendWelcomeEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Welcome to Our Service");
        message.setText(
              "Dear User,\n\nWelcome to our service! We are glad to have you on board.\n\nBest Regards,\nYour Company");

        mailSender.send(message);
    }
}
