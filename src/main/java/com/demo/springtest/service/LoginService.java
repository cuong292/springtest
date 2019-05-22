package com.demo.springtest.service;

import com.demo.springtest.dao.LoginDAO;
import com.demo.springtest.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {
    @Autowired
    LoginDAO mLoginDAO;

    @Autowired
    JwtService tokenService;

    @Transactional
    public String login(Account account) {
        int userId = mLoginDAO.login(account.getUserName(), account.getPassword());
        if (userId != 0) {
            return tokenService.generateToken(userId);
        }
        return null;
    }

}
