package com.melody.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * Created by xiaobai on 16-8-5.
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class ArticleServiceTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private ArticleService articleService;



}
