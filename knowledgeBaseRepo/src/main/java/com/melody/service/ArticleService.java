package com.melody.service;

import com.melody.dao.ArticleDao;
import com.melody.domain.Article;
import com.melody.domain.StatusInfo;
import com.melody.web.ArticleInfo;
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

    public ArticleInfo getArticleInfoFromFile(String fileName){
        return articleDao.getArticleInfoFromFile(fileName);
    }

}
