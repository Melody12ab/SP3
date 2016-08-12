package com.melody.dao;

import com.melody.domain.Article;
import com.melody.domain.StatusInfo;
import com.melody.utils.DownloadImageUtils;
import com.melody.utils.JSOUPUtils;
import com.melody.web.ArticleInfo;
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

    //获取资源数据
    public Article getArticleByUrl(String url){
        Article article=new Article();
        Map<String,String> infos=JSOUPUtils.getArticleInfoFromUrl(url);
        article.setTitle(infos.get("title"));
        article.setContent(infos.get("content"));
        article.setCode(200);
        return article;
    }
    //保存文章信息到html
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
                    navList.append("<li class='active'><a href='#" + contents[i].replace("/","\\/") + "' data-toggle='tab'>" + contents[i] + "</a></li>");
                    small.append("<div class='tab-pane fade in active' id='" + contents[i] + "'>" + contents[++i] + "</div>");
                }else{
                    navList.append("<li class><a href='#" + contents[i].replace("/","\\/") + "' data-toggle='tab'>" + contents[i] + "</a></li>");
                    small.append("<div class='tab-pane fade ' id='" + contents[i] + "'>" + contents[++i] + "</div>");
                }
            }
        }
        template=template.replace("nnaavvLLiisstt",navList.toString()).replace("ssmmaallll",small.toString());
        //下载图片
        template= DownloadImageUtils.downloadImageFromHtml(template);
        try {
            FileUtils.writeStringToFile(new File(savePath+"/html/"+fileName+".html"),template,"UTF-8",false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        template=null;
        statusInfo.setCode(200);
        statusInfo.setMsg("保存成功！");
        return statusInfo;
    }
    //从文件中读取html
    public ArticleInfo getArticleInfoFromFile(String fileName){
        return JSOUPUtils.getArticleInfoFromFile(fileName);
    }

    public static void main(String[] args) {
        String a="nihao/wohao";
        System.out.println(a);
        System.out.println(a.replace("/","\\/"));
    }

}
