package com.demo.springtest.service;

import com.demo.springtest.dao.AuthenticationDAO;
import com.demo.springtest.dto.UserProfileDTO;
import com.demo.springtest.entity.Account;
import com.mchange.net.SmtpMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {
    @Autowired
    AuthenticationDAO mAuthenticationDAO;

    @Autowired
    JwtService tokenService;

    @Transactional
    public String login(Account account) {
        int userId = mAuthenticationDAO.login(account.getUserName(), account.getPassword());
        if (userId != 0) {
            return tokenService.generateToken(userId);
        }
        return null;
    }

    @Transactional
    public boolean register(UserProfileDTO userProfileDTO) {
        return mAuthenticationDAO.register(userProfileDTO);
    }

}
