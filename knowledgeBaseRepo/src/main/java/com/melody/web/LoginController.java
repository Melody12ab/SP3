package com.melody.web;

import com.melody.domain.User;
import com.melody.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xiaobai on 16-8-4.
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login.melody")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/main.melody")
    String mainPage() {
        return "main";
    }

    @RequestMapping("/loginCheck.melody")
    public ModelAndView loginCheck(HttpServletRequest req, LoginCommand loginCommand) {
        boolean isValidUser = userService.hasMatchUser(loginCommand.getUserName(), DigestUtils.sha1Hex(loginCommand.getPassword()));
        if (!isValidUser) {
            return new ModelAndView("login", "error", "用户名或密码错误啦！！！！");
        } else {
            User user = userService.findUserByuserName(loginCommand.getUserName());
            req.getSession().setAttribute("user", user);
            return new ModelAndView("redirect:/admin/main.melody");
        }
    }

}
