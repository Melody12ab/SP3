package com.melody.service;

import com.melody.dao.UserDao;
import com.melody.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiaobai on 16-8-4.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public boolean hasMatchUser(String userName,String password){
        return userDao.getMatchCount(userName,password)>0;
    }

    public User findUserByuserName(final String userName){
        return userDao.findUserByUserName(userName);
    }

}
