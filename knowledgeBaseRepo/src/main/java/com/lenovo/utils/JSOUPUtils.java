package com.lenovo.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaobai on 16-8-5.
 */
public class JSOUPUtils {
    public static Map<String,String> getArticleInfoFromUrl(String url){
        Map<String,String> infos=new HashMap<String, String>();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(3000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = doc.title();
        String content=doc.select(".describe").html();

        //对内容的一次过滤
        Elements aEelements=doc.getElementsByTag("a");
        for(Element e:aEelements){
            String outerh=e.outerHtml().replace("\"","\\\"");
            if(outerh.contains("kid")){
                content=content.replaceAll(outerh,e.text());
            }
        }

        //fill in full img link
        String baseImageUrl="http://iknow.lenovo.com";
        Elements imgEelements = doc.getElementsByTag("img");
        for(Element e:imgEelements){
            String esrc=e.attr("src");
            if (!esrc.contains("http")){
                content = content.replaceAll(esrc,baseImageUrl+esrc);
            }
        }

        infos.put("title",title);
        infos.put("content",content);
        return infos;
    }
}
