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
    public String login(UserProfileDTO account) {
        return mAuthenticationDAO.login(account.getEmail(), account.getPassword());
    }

    @Transactional
    public boolean register(UserProfileDTO userProfileDTO) {
        return mAuthenticationDAO.register(userProfileDTO);
    }

}
