package com.lenovo.web;

import com.lenovo.domain.Article;
import com.lenovo.domain.StatusInfo;
import com.lenovo.service.ArticleService;
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

    @RequestMapping(value = "/getArticle.lenovo",method = {RequestMethod.GET})
    public @ResponseBody Article getArticleInJson(@RequestParam("url") String url){
        return articleService.getArticleByUrl(url);
    }

    @RequestMapping("/preview.lenovo")
    public String getPreview(){
        return "preview";
    }

    @RequestMapping(value = "/save.lenovo",method = {RequestMethod.POST})
    public @ResponseBody StatusInfo saveArticle(@RequestBody ArticleInfo articleInfo,HttpServletRequest request){
        String savePath=request.getSession().getServletContext().getRealPath("/html/");
        return articleService.saveArticleInfo(articleInfo,savePath);
    }


    @RequestMapping(value = "/config.lenovo")
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
