package com.melody.utils;

import com.melody.web.ArticleInfo;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by xiaobai on 16-8-5.
 */
public class JSOUPUtils {
    public static Map<String, String> getArticleInfoFromUrl(String url) {
        Map<String, String> infos = new HashMap<String, String>();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(3000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = doc.title();
        String content = doc.select(".describe").html();

        //对内容的一次过滤
        Elements aEelements = doc.getElementsByTag("a");
        for (Element e : aEelements) {
            String outerh = e.outerHtml().replace("\"", "\\\"");
            if (outerh.contains("kid")) {
                content = content.replaceAll(outerh, e.text());
            }
        }

        //fill in full img link
        String baseImageUrl = "http://iknow.lenovo.com";
        Elements imgEelements = doc.getElementsByTag("img");
        for (Element e : imgEelements) {
            String esrc = e.attr("src");
            if (!esrc.contains("http")) {
                content = content.replaceAll(esrc, baseImageUrl + esrc);
            }
        }

        infos.put("title", title);
        infos.put("content", content);
        return infos;
    }

    public static ArticleInfo getArticleInfoFromFile(String fileName) {
        ArticleInfo articleInfo=new ArticleInfo();
        Properties pron = new Properties();
        try {
            pron.load(JSOUPUtils.class.getResourceAsStream("/info.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = pron.getProperty("openFilePath");
        String html = null;
        try {
            //todo 文件是否存在
            html = FileUtils.readFileToString(new File(path + "/" + fileName + ".html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(html);
        articleInfo.setTitle(doc.title());
        articleInfo.setFileName(fileName);
        Elements navs=doc.select(".navList a");
        Elements smalls=doc.select(".small div");
        int len=smalls.size();
        String [] contents=new String[len*2];
        for(int i=0,offset=0;i<len;i++){
            if(navs.size()==0){
                contents[offset]="untitle";
            }else{
                contents[offset]=navs.get(i).ownText();
            }
            offset++;
            contents[offset]=smalls.get(i).html();
            offset++;
        }
        articleInfo.setContent(contents);
        return articleInfo;
    }
}
