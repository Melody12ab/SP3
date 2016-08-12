package com.melody.web;

import com.melody.domain.Article;
import com.melody.domain.StatusInfo;
import com.melody.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by xiaobai on 16-8-5.
 */
@Controller
@RequestMapping("/admin")
public class MainControlller {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/getArticle.melody",method = {RequestMethod.GET})
    public @ResponseBody Article getArticleInJson(@RequestParam("url") String url){
        return articleService.getArticleByUrl(url);
    }

    @RequestMapping("/preview.melody")
    public String getPreview(){
        return "preview";
    }

    @RequestMapping(value = "/save.melody",method = {RequestMethod.POST})
    public @ResponseBody StatusInfo saveArticle(@RequestBody ArticleInfo articleInfo,HttpServletRequest request){
        String savePath=request.getSession().getServletContext().getRealPath("/");
        return articleService.saveArticleInfo(articleInfo,savePath);
    }

    @RequestMapping(value = "/openFile.melody",method = {RequestMethod.GET})
    public @ResponseBody ArticleInfo getArticleInfo(@RequestParam("fileName") String fileName){
        return articleService.getArticleInfoFromFile(fileName);
    }


    @RequestMapping(value = "/config.melody")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String action = request.getParameter("action");
        String rootPath = request.getSession()
                .getServletContext().getRealPath("/");
        try {
            String exec = new com.baidu.ueditor.ActionEnter(request, rootPath).exec();
            if( action!=null &&
                    (action.equals("listfile") || action.equals("listimage") ) ){
                rootPath = rootPath.replace("\\", "/");
                exec = exec.replaceAll(rootPath, "");
            }
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }

}
