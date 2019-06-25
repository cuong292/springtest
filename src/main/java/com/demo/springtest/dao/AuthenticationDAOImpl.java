package com.demo.springtest.dao;

import com.demo.springtest.dto.UserProfileDTO;
import com.demo.springtest.entity.Account;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import java.net.URL;

@Repository
public class AuthenticationDAOImpl implements AuthenticationDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    FirebaseAuth firebaseAuth;

    @Autowired
    MailSender mailSender;

    @Override
    public int login(String username, String password) {
        return 0;
    }

    @Override
    public boolean register(UserProfileDTO userProfileDTO) {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(userProfileDTO.getEmail())
                .setEmailVerified(false)
                .setPassword(userProfileDTO.getPassword())
                .setPhoneNumber(userProfileDTO.getPhoneNumber())
                .setDisplayName(userProfileDTO.getUserName())
                .setDisabled(false);
        UserRecord record = null;
        try {
            record = firebaseAuth.createUser(request);
            saveUserProfile(record, userProfileDTO);
            createEmailConfirmationLink(userProfileDTO.getEmail());
            return true;
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createEmailConfirmationLink(String email) {
        try {
            String link = firebaseAuth.generateSignInWithEmailLink(
                    email, ActionCodeSettings.builder().setUrl("https://wikipedia.org").build());
            // Construct email verification template, embed the link and send
            // using custom SMTP server.
            sendUserVerificationEmail(email, link);
        } catch (Exception e) {
            System.out.println("Error generating email link: " + e.getMessage());
        }
    }

    private void sendUserVerificationEmail(String email, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply.no@gmail.com");
        message.setTo(email);
        message.setText("Verify email : " + link);
        mailSender.send(message);
    }

    private void saveUserProfile(UserRecord record, UserProfileDTO userProfileDTO) {
        Session session = sessionFactory.getCurrentSession();
        Account account = new Account(userProfileDTO.getEmail(), userProfileDTO.getPassword(), record.getUid());
        session.saveOrUpdate(account);
    }
}
