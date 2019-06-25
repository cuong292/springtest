package com.demo.springtest.dao;

import com.demo.springtest.dto.UserProfileDTO;

public interface AuthenticationDAO {
    int login(String username,String password);

    boolean register(UserProfileDTO userProfileDTO);
}
