package com.demo.springtest.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LoginDAOImpl implements LoginDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public boolean login(String username, String password) {
        return false;
    }
}
