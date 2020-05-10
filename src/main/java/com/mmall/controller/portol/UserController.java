package com.mmall.controller.portol;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 *@author suny
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @PostMapping(value = "login.do")
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response=userService.login(username,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @PostMapping("logout.do")
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @PostMapping("register.do")
    @ResponseBody
    public ServerResponse<String> register(User user){
        return userService.register(user);
    }


    @PostMapping("check_valid.do")
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
        return userService.checkValid(str,type);
    }

    @PostMapping("get_user_info.do")
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user!=null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
    }

    public ServerResponse<String> forgetGetQuestion(String username){
        return null;
    }

}
