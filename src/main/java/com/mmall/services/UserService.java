package com.mmall.services;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str,String type);
}
