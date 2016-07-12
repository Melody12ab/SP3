package com.melody.service;

import com.melody.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * Created by xiaobai on 16-7-12.
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class UserServiceTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private UserService userService;

    @Test
    public void hasMatchUser(){
        boolean b1=userService.hasMatchUser("admin","123456");
        boolean b2=userService.hasMatchUser("ad","123456");
        boolean b3=userService.hasMatchUser("admin","12345");
        assertTrue(b1);
        assertTrue(!b2);
        assertTrue(!b3);
    }
    @Test
    public void findUserByUserName(){
        User user=userService.findUserByUserName("admin");
        assertEquals(user.getUserName(),"admin");
    }

}
