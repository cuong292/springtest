package com.demo.springtest.dao;

import com.demo.springtest.dto.UserProfileDTO;
import com.demo.springtest.entity.Account;
import com.demo.springtest.entity.User;
import com.demo.springtest.exception.CommonException;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class AuthenticationDAOImpl implements AuthenticationDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    FirebaseAuth firebaseAuth;

    @Autowired
    MailSender mailSender;

    @Override
    public String login(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        String qr = "from Account WHERE email = ?0 AND password = ?1";
        Query query = session.createQuery(qr);
        query.setParameter(0, username);
        query.setParameter(1, password);
        List<Object> result = query.getResultList();
        if (result.size() == 0) {
            throw new CommonException("Wrong username / password");
        } else {
            Account account = (Account) result.get(0);
            try {
                return FirebaseAuth.getInstance().createCustomToken(account.getUid());
            } catch (Exception e) {
                throw new CommonException(e.getMessage());
            }
        }
    }

    @Override
    public boolean register(UserProfileDTO userProfileDTO) {
        if (userExist(userProfileDTO.getEmail())) {
            throw new CommonException("Your email has been used");
        }
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean userExist(String email) {
        Session session = sessionFactory.getCurrentSession();
        String querys = "from Account WHERE email = ?0";
        Query query = session.createQuery(querys);
        query.setParameter(0, email);
        return query.list().size() != 0;
    }

    private void createEmailConfirmationLink(String email) {
        try {
            String link = firebaseAuth.generateEmailVerificationLink(
                    email, ActionCodeSettings.builder().setUrl("https://nakedphase2.page.link/qLo3").setHandleCodeInApp(false).build());
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
