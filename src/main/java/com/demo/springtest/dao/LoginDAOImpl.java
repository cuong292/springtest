package com.demo.springtest.dao;

import com.demo.springtest.entity.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Repository
public class LoginDAOImpl implements LoginDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public int login(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Account as a where a.userName = ?1 and a.password = ?2", Account.class);
        query.setParameter(1,username);
        query.setParameter(2, password);
        List<Account> userAccounts = query.getResultList();
        if (userAccounts.size() != 0) {
            return userAccounts.get(0).getUserId();
        }
        return 0;
    }
}
