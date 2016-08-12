package com.melody.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * Created by xiaobai on 16-8-4.
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class UserServiceTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private UserService userService;

    @Test
    public void hasMatchUser(){
        boolean b1=userService.hasMatchUser("test", "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3 ");
        boolean b2=userService.hasMatchUser("nihao","wohao");
        assertTrue(b1);
        assertTrue(!b2);
    }

}
