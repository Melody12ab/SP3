package com.lenovo.service;

import com.lenovo.dao.ArticleDao;
import com.lenovo.domain.Article;
import com.lenovo.domain.StatusInfo;
import com.lenovo.web.ArticleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiaobai on 16-8-5.
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    public Article getArticleByUrl(String url){
        return articleDao.getArticleByUrl(url);
    }

    public StatusInfo saveArticleInfo(ArticleInfo articleInfo,String path){
        return articleDao.saveArticleInfo(articleInfo,path);
    }

}
