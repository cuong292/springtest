package com.demo.springtest.service;

import com.demo.springtest.dao.AuthenticationDAO;
import com.demo.springtest.dto.AccountDTO;
import com.demo.springtest.dto.UserProfileDTO;
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
    public String login(AccountDTO account) {
        return mAuthenticationDAO.login(account.getEmail(), account.getPassword());
    }

    @Transactional
    public boolean register(UserProfileDTO userProfileDTO) {
        return mAuthenticationDAO.register(userProfileDTO);
    }

    @Transactional
    public boolean resetPassword(String email) {
        return mAuthenticationDAO.resetPassword(email);
    }

    @Transactional
    public boolean saveUserResetPassword(String email) {
        return false;
    }

}
