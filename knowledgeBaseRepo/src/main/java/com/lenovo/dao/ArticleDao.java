package com.lenovo.dao;

import com.lenovo.domain.Article;
import com.lenovo.domain.StatusInfo;
import com.lenovo.utils.JSOUPUtils;
import com.lenovo.web.ArticleInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by xiaobai on 16-8-5.
 */
@Repository
public class ArticleDao {

    public Article getArticleByUrl(String url){
        Article article=new Article();
        Map<String,String> infos=JSOUPUtils.getArticleInfoFromUrl(url);
        article.setTitle(infos.get("title"));
        article.setContent(infos.get("content"));
        article.setCode(200);
        return article;
    }

    public StatusInfo saveArticleInfo(ArticleInfo articleInfo,String savePath){
        StatusInfo statusInfo=new StatusInfo();
        String template=null;
        StringBuilder navList=new StringBuilder();
        StringBuilder small=new StringBuilder();
        String templatePath=ArticleDao.class.getClassLoader().getResource("template.txt").getPath();
        try {
            template=FileUtils.readFileToString(new File(templatePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        template=template.replaceAll("\\{\\{title\\}\\}",articleInfo.getTitle());
        String [] contents=articleInfo.getContent();
        String fileName=articleInfo.getFileName();
        for(int i=0,len=contents.length;i<len;i++){
            if(contents[0].trim().equals("untitle")){
                small.append("<div class='tab-pane fade in active' id='" + contents[0] + "'>" + contents[++i]+ "</div>");
            }else{
                if(i==0){
                    navList.append("<li class='active'><a href='#" + contents[i] + "' data-toggle='tab'>" + contents[i] + "</a></li>");
                    small.append("<div class='tab-pane fade in active' id='" + contents[i] + "'>" + contents[++i] + "</div>");
                }else{
                    navList.append("<li class><a href='#" + contents[i] + "' data-toggle='tab'>" + contents[i] + "</a></li>");
                    small.append("<div class='tab-pane fade ' id='" + contents[i] + "'>" + contents[++i] + "</div>");
                }
            }
        }
        template=template.replace("nnaavvLLiisstt",navList.toString()).replace("ssmmaallll",small.toString());
        try {
            FileUtils.writeStringToFile(new File(savePath+"/"+fileName+".html"),template,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        template=null;
        statusInfo.setCode(200);
        statusInfo.setMsg("保存成功！");
        return statusInfo;
    }

}
