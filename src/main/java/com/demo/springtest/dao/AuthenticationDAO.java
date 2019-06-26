package com.demo.springtest.dao;

import com.demo.springtest.dto.UserProfileDTO;

public interface AuthenticationDAO {
    String login(String username,String password);

    boolean register(UserProfileDTO userProfileDTO);
}
