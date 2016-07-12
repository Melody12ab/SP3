package com.melody.service;

import com.melody.dao.LoginLogDao;
import com.melody.dao.UserDao;
import com.melody.domain.Loginlog;
import com.melody.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiaobai on 16-7-12.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginLogDao loginLogDao;

    public boolean hasMatchUser(String userName,String password){
        return userDao.getMatchCount(userName,password)>0;
    }

    public User findUserByUserName(String userName){
        return userDao.findUserByUserName(userName);
    }

    public void loginSuccess(User user){
        Loginlog loginlog=new Loginlog();
        loginlog.setIp(user.getLastIp());
        loginlog.setLoginDate(user.getLastVisit());
        loginlog.setUserId(user.getUserId());
        loginLogDao.insertLoginlog(loginlog);
    }

}
